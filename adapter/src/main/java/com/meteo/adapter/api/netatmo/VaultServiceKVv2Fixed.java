package com.meteo.adapter.api.netatmo;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.api.Logical;
import io.github.jopenlibs.vault.response.LogicalResponse;
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

        this.vault = Vault.create(config).logical();
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
        return response.getData();
    }
}