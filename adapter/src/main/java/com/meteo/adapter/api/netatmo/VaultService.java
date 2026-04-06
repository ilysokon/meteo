package com.meteo.adapter.api.netatmo;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.api.Logical;
import com.bettercloud.vault.response.AuthResponse;
import com.bettercloud.vault.response.LogicalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

@Singleton
public class VaultService {
    private static final Logger LOG = LoggerFactory.getLogger(VaultService.class);
    private static final String EXPIRES_AT = "expires_at";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String VAULT_PATH = "secret/data/netatmo";

    private final Logical vault;

    public VaultService() throws VaultException, IOException {
        //        VaultConfig config = new VaultConfig()
        //                .address(System.getenv("VAULT_ADDR"))
        //                .token(System.getenv("VAULT_TOKEN"))
        //                .build();

        Path jwtPath = Path.of("/var/run/secrets/kubernetes.io/serviceaccount/token");
        String jwt = Files.readString(jwtPath);

        // Build config WITHOUT token
        VaultConfig config = new VaultConfig()
                .address(System.getenv("VAULT_ADDR"))
                .nameSpace(System.getenv("VAULT_NAMESPACE"))
                .build();

        Vault vault = new Vault(config);

        // Login with Kubernetes Auth Method
        AuthResponse auth = vault.auth()
                .loginByKubernetes("netatmo-secret-writer", jwt);

        // Extract Vault token
        String vaultToken = auth.getAuthClientToken();

        // Create a new Vault client using this token
        this.vault = new Vault(
                new VaultConfig()
                        .address(System.getenv("VAULT_ADDR"))
                        .token(vaultToken)
                        .nameSpace(System.getenv("VAULT_NAMESPACE"))
                        .build()
        ).logical();

        // this.vault = new Vault(config).logical();
    }

    // ================= GETTERS ==================

    public Optional<String> getAccessToken() throws VaultException, JsonProcessingException {
        Object accessToken = read(VAULT_PATH).get(ACCESS_TOKEN);
        return accessToken != null ? Optional.of((String) accessToken) : Optional.empty();
    }

    public Optional<Long> getExpiresAt() throws VaultException, JsonProcessingException {
        Object expiresIn = read(VAULT_PATH).get(EXPIRES_AT);
        return expiresIn != null ? Optional.of((Long) expiresIn) : Optional.empty();
    }

    public Optional<String> getRefreshToken() throws VaultException, JsonProcessingException {
        Object refreshToken = read(VAULT_PATH).get(REFRESH_TOKEN);
        return refreshToken != null ? Optional.of((String) refreshToken) : Optional.empty();
    }

    // ================= WRITERS ==================

    /**
     * Stores all 3 values atomically in Vault.
     */
    public void saveTokens(String accessToken, String refreshToken, long expiresAt) throws VaultException {
        Map<String, Object> data = Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken,
                EXPIRES_AT, expiresAt
        );

        write(VAULT_PATH, data);
    }

    // ================= INTERNAL ==================

    private Map<String, Object> read(String path) throws VaultException, JsonProcessingException {
        LogicalResponse response = vault.read(path);
        LOG.info("Response: {}", response);

        Map<String, String> data = response.getData(); // this is a JSON string
        LOG.info("Response.getData(): {}", data);

        if (data.isEmpty()) {
            return Map.of();
        }

        String jsonInner = data.get("data");
        LOG.info("data.get(\"data\"): {}", jsonInner);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonInner, Map.class);
    }

    private void write(String path, Map<String, Object> data) throws VaultException {
        vault.write(path, Map.of("data", data));
    }
}

