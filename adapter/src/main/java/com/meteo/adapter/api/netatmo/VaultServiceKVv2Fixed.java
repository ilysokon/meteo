package com.meteo.adapter.api.netatmo;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.api.Logical;
import com.bettercloud.vault.response.LogicalResponse;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Singleton
public class VaultServiceKVv2Fixed {

    private static final Logger LOG = LoggerFactory.getLogger(VaultServiceKVv2Fixed.class);

    private static final String PATH = "secret/netatmo";

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRES_AT = "expires_at";

    private final Logical vault;

    public VaultServiceKVv2Fixed() throws VaultException, IOException {
        VaultConfig config = new VaultConfig()
                .address(System.getenv("VAULT_ADDR"))
                .token(System.getenv("VAULT_TOKEN"))
                .nameSpace(System.getenv("VAULT_NAMESPACE"))
                .build();

        this.vault = new Vault(config).logical();
    }

    // ================= READ METHODS =================

    public Optional<String> getAccessToken() throws VaultException {
        return readField(ACCESS_TOKEN);
    }

    public Optional<String> getRefreshToken() throws VaultException {
        return readField(REFRESH_TOKEN);
    }

    public Optional<Long> getExpiresAt() throws VaultException {
        Optional<String> value = readField(EXPIRES_AT);
        return value.map(Long::valueOf);
    }

    // ================= INTERNAL READ =================

    private Optional<String> readField(String key) throws VaultException {
        Map<String, String> data = readAll();

        String value = data.get(key);

        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    private Map<String, String> readAll() throws VaultException {
        LogicalResponse response = vault.read(PATH);

        if (response == null || response.getData() == null) {
            return Map.of();
        }

        LOG.debug("Vault response raw: {}", response.getData());

        // KV v2-safe: already flattened correctly by BetterCloud client
        return (Map<String, String>) response.getData();
    }

    // ================= WRITE =================

    public void saveTokens(String accessToken, String refreshToken, long expiresAt) throws VaultException {
        Map<String, Object> payload = Map.of(
                ACCESS_TOKEN, accessToken,
                REFRESH_TOKEN, refreshToken,
                EXPIRES_AT, expiresAt
        );

        writeAll(payload);
    }

    private void writeAll(Map<String, Object> data) throws VaultException {
        vault.write(PATH, Map.of("data", data));
        LOG.info("Vault updated at path {}", PATH);
    }
}