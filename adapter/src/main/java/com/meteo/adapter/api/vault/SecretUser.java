package com.meteo.adapter.api.vault;

import jakarta.inject.Singleton;

@Singleton
public class SecretUser {
    private final VaultClient vaultClient;

    public SecretUser(VaultClient vaultClient) {
        this.vaultClient = vaultClient;
    }

    public String getToken() {
        Object val = vaultClient.getSecret().get("token");
        return val == null ? null : val.toString();
    }
}
