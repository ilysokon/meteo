package com.meteo.adapter.api.vault;

import com.bettercloud.vault.VaultConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class VaultAuthenticator {
    private static final Logger log = LoggerFactory.getLogger(VaultAuthenticator.class);
    private final VaultConfig config;
    private final HttpClient http;
    private final ObjectMapper mapper = new ObjectMapper();

    private final AtomicReference<String> clientToken = new AtomicReference<>(null);
    private final AtomicReference<Instant> tokenExpiry = new AtomicReference<>(Instant.EPOCH);

    public VaultAuthenticator(VaultConfig config) {
        this.config = config;
        this.http = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public synchronized String getValidToken() throws Exception {
        Instant now = Instant.now();
        Instant expiry = tokenExpiry.get();
        String token = clientToken.get();

        // Renew if missing or going to expire within 30s
        if (token == null || now.isAfter(expiry.minusSeconds(30))) {
            authenticate();
        }
        return clientToken.get();
    }

    private void authenticate() throws Exception {
        log.info("Authenticating to Vault via Kubernetes auth (role={})", config.vaultKubernetesRole);
        String jwt = Files.readString(Path.of(config.k8sJwtPath));

        Map<String, Object> payload = Map.of("role", config.vaultKubernetesRole, "jwt", jwt);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(config.vaultAddress + "/v1/auth/kubernetes/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Vault auth failed: " + resp.statusCode() + " " + resp.body());
        }

        Map<String, Object> json = mapper.readValue(resp.body(), Map.class);
        Map<String, Object> auth = (Map<String, Object>) json.get("auth");
        String token = (String) auth.get("client_token");
        Integer leaseDuration = (Integer) auth.getOrDefault("lease_duration", 3600);

        clientToken.set(token);
        tokenExpiry.set(Instant.now().plusSeconds(leaseDuration));
        log.info("Vault token acquired, ttl={}s", leaseDuration);
    }
}
