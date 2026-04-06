package com.meteo.adapter.api.vault;

import com.bettercloud.vault.VaultConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Thread-safe Vault client that:
 *  - logs in via Kubernetes auth (VaultAuthenticator)
 *  - caches KV-v2 secret data
 *  - polls KV metadata for version changes and refreshes secret
 *  - exposes getSecret() for callers
 */
public class VaultClient {

    private static final Logger log = LoggerFactory.getLogger(VaultClient.class);

    private final VaultAuthenticator authenticator;
    private final VaultConfig config;
    private final HttpClient http;
    private final ObjectMapper mapper = new ObjectMapper();

    // cached secret and metadata
    private volatile Map<String, Object> cachedSecret = Collections.emptyMap();
    private volatile int cachedVersion = -1;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1, r -> {
        Thread t = new Thread(r, "vault-client-scheduler");
        t.setDaemon(true);
        return t;
    });

    public VaultClient(VaultAuthenticator authenticator, VaultConfig config) {
        this.authenticator = authenticator;
        this.config = config;
        this.http = HttpClient.newHttpClient();

        // initial load synchronously
        try {
            refreshSecret();
        } catch (Exception e) {
            log.warn("Initial secret load failed: {}", e.getMessage());
        }

        // schedule metadata polling
        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (hasNewVersion()) {
                    refreshSecret();
                }
            } catch (Exception e) {
                log.error("Error polling Vault metadata: {}", e.getMessage(), e);
            }
        }, config.metadataPollSeconds, config.metadataPollSeconds, TimeUnit.SECONDS);
    }

    /**
     * Get secret (cached). This method ensures a fresh secret is returned
     * by refreshing if cache is empty.
     */
    public Map<String, Object> getSecret() {
        if (cachedSecret == null || cachedSecret.isEmpty()) {
            try {
                refreshSecret();
            } catch (Exception e) {
                log.error("Failed to refresh secret on demand: {}", e.getMessage(), e);
            }
        }
        return cachedSecret;
    }

    /**
     * Force refresh of the secret from Vault KV v2 path
     */
    public synchronized void refreshSecret() throws Exception {
        String token = authenticator.getValidToken();
        String path = config.kvPath; // e.g. secret/data/myapp

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(config.getAddress() + "/v1/" + path))
                .header("X-Vault-Token", token)
                .GET().build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Failed to read secret: " + resp.statusCode() + " " + resp.body());
        }

        Map<String, Object> body = mapper.readValue(resp.body(), Map.class);
        Map<String, Object> data = (Map<String, Object>) ((Map) body.get("data")).get("data");
        Integer version = (Integer) ((Map) ((Map) body.get("data")).get("metadata")).get("version");

        cachedSecret = data;
        cachedVersion = (version != null) ? version : cachedVersion;
        log.info("Secret refreshed from Vault, version={}", cachedVersion);
    }

    /**
     * Read KV v2 metadata to detect version changes.
     */
    private boolean hasNewVersion() throws Exception {
        String token = authenticator.getValidToken();
        // metadata endpoint: secret/metadata/myapp
        String metadataPath = config.kvPath.replaceFirst("/data/", "/metadata/");
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(config.vaultAddress + "/v1/" + metadataPath))
                .header("X-Vault-Token", token)
                .GET().build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            log.warn("Metadata check returned {}: {}", resp.statusCode(), resp.body());
            return false;
        }

        Map<String, Object> body = mapper.readValue(resp.body(), Map.class);
        Map<String, Object> data = (Map<String, Object>) body.get("data");
        Integer current = (Integer) data.get("current_version");

        if (current != null && current != cachedVersion) {
            log.info("Detected new secret version: {} -> {}", cachedVersion, current);
            return true;
        }
        return false;
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}

