package com.meteo.crypto;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.RegistryConfiguration;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.PredefinedAeadParameters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AccessKeyCrypto {

    private final Aead aead;

    public AccessKeyCrypto(KeysetHandle keyset)
            throws Exception {
        AeadConfig.register();
        this.aead = keyset.getPrimitive(
                RegistryConfiguration.get(), Aead.class);
    }

    public String encrypt(String accessKey)
            throws Exception {

        byte[] plaintext = accessKey.getBytes(
                StandardCharsets.UTF_8);

        byte[] aad = "access-key:v1".getBytes(
                StandardCharsets.UTF_8);

        byte[] ciphertext = aead.encrypt(plaintext, aad);

        return Base64.getEncoder()
                .encodeToString(ciphertext);
    }

    public String decrypt(String encrypted)
            throws Exception {

        byte[] ciphertext = Base64.getDecoder()
                .decode(encrypted);

        byte[] aad = "access-key:v1".getBytes(
                StandardCharsets.UTF_8);

        byte[] plaintext = aead.decrypt(ciphertext, aad);

        return new String(plaintext, StandardCharsets.UTF_8);
    }

    // Demonstration only: generate a keyset once,
    // then protect and persist it separately.
    public static KeysetHandle generateKeyset()
            throws Exception {
        AeadConfig.register();
        return KeysetHandle.generateNew(
                PredefinedAeadParameters.AES256_GCM);
    }
}
