package com.meteo.adapter.api.netatmo;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.api.Logical;
import com.bettercloud.vault.json.JsonValue;
import com.bettercloud.vault.response.LogicalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class VaultServiceKVv2 {
    private static final Logger LOG = LoggerFactory.getLogger(VaultServiceKVv2.class);

    private static final String VAULT_PATH = "secret/data/netatmo";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRES_AT = "expires_at";
    public static final String DATA = "data";

    private final Logical vault;

    public VaultServiceKVv2() throws Exception {
        // Kubernetes JWT login
//        Path jwtPath = Path.of("/var/run/secrets/kubernetes.io/serviceaccount/token");
//        String jwt = Files.readString(jwtPath);
//
//        VaultConfig config = new VaultConfig()
//                .address(System.getenv("VAULT_ADDR"))
//                .nameSpace(System.getenv("VAULT_NAMESPACE"))
//                .token(System.getenv("VAULT_TOKEN"))
//                .engineVersion(2)
//                .build();
//
//        Vault vaultClient = new Vault(config);
//
//        AuthResponse auth = vaultClient.auth()
//                .loginByKubernetes("netatmo-secret-writer", jwt);
//
//        String vaultToken = auth.getAuthClientToken();

        this.vault = new Vault(
                new VaultConfig()
                        .address(System.getenv("VAULT_ADDR"))
                        .token(System.getenv("VAULT_TOKEN"))
                        .nameSpace(System.getenv("VAULT_NAMESPACE"))
                        .engineVersion(2)
                        .build()
        ).logical();
    }

    VaultServiceKVv2(Logical vault) {
        this.vault = vault;
    }

    public void saveTokens(String accessToken, String refreshToken, long expiresAt) throws VaultException {
        Map<String, Object> data = Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken,
                EXPIRES_AT, expiresAt
        );

        // KV v2 expects {"data": {...}}
        vault.write(VAULT_PATH, Map.of(DATA, data));
    }

    public Map<String, String> readTokens() throws VaultException {
        LogicalResponse response = vault.read(VAULT_PATH);
        if (response == null || response.getData() == null) {
            return Map.of();
        }

        // unwrap KV v2 inner "data"
        final Map<String, String> result = new HashMap<>();
        if (response.getDataObject() != null && !response.getDataObject().isEmpty()) {
            JsonValue innerData = response.getDataObject().get(DATA);
            if (innerData == null) {
                return Map.of();
            }

            String rawData = innerData.toString(); // ""{refresh_token=..., access_token=..., expires_at=...}""

            // Remove curly braces and extra quote
            // The rawData is like this: ""{...}""
            rawData = rawData.substring(2, rawData.length() - 2);

            // Split key-value pairs
            for (String pair : rawData.split(",\\s*")) {
                int idx = pair.indexOf('=');
                if (idx != -1) {
                    String key = pair.substring(0, idx).trim();
                    String value = pair.substring(idx + 1).trim();
                    result.put(key, value);
                }
            }

            String accessToken = result.get(ACCESS_TOKEN);
            String refreshToken = result.get(REFRESH_TOKEN);
            String expiresAt = result.get(EXPIRES_AT);
        } else {
            LOG.info("response.getDataObject() is null or empty");
        }

        return result;
    }

    public Optional<String> getAccessToken() throws VaultException, JsonProcessingException {
        return Optional.ofNullable(readTokens().get(ACCESS_TOKEN));
    }

    public Optional<String> getRefreshToken() throws VaultException, JsonProcessingException {
        return Optional.ofNullable(readTokens().get(REFRESH_TOKEN));
    }

    public Optional<Long> getExpiresAt() throws VaultException, JsonProcessingException {
        String val = readTokens().get(EXPIRES_AT);
        return val != null ? Optional.of(Long.parseLong(val)) : Optional.empty();
    }

    private Map<String, String> readKVv2() throws VaultException, JsonProcessingException {
        return read(VAULT_PATH);
    }

    private Map<String, String> read(String path) throws VaultException, JsonProcessingException {
        LogicalResponse response = vault.read(path);
        if (response == null || response.getData() == null) {
            return Map.of();
        }

        // KV v2: outer "data"
        Object outerDataObj = response.getData().get(DATA);
        if (!(outerDataObj instanceof Map)) {
            return Map.of();
        }

        Map<String, Object> outerData = (Map<String, Object>) outerDataObj;

        // Inner "data" map
        Object innerDataObj = outerData.get(DATA);
        if (!(innerDataObj instanceof Map)) {
            // Some versions only wrap once, use outerData directly
            return outerData.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
        }

        Map<String, Object> innerData = (Map<String, Object>) innerDataObj;

        return innerData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }

}
