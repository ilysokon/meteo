package com.meteo.adapter.api.vault;

import com.bettercloud.vault.VaultConfig;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class VaultFactory {

    @Singleton
    public VaultConfig vaultConfig() {
        return new VaultConfig();
    }

    @Singleton
    public VaultAuthenticator vaultAuthenticator(VaultConfig config) {
        return new VaultAuthenticator(config);
    }

    @Singleton
    public VaultClient vaultClient(VaultAuthenticator auth, VaultConfig config) {
        return new VaultClient(auth, config);
    }
}