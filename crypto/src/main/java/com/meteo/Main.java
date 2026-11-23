package com.meteo;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.RegistryConfiguration;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.PredefinedAeadParameters;
import com.meteo.crypto.CredentialCrypto;

import java.nio.ByteBuffer;

public class Main {

    static void main() throws Exception {
        AeadConfig.register();

        KeysetHandle keyset = KeysetHandle.generateNew(
                PredefinedAeadParameters.AES256_GCM
        );

        Aead aead = keyset.getPrimitive(
                RegistryConfiguration.get(),
                Aead.class
        );

        CredentialCrypto crypto = new CredentialCrypto(aead);

        String credentialId = "netatmo-access-key";
        String accessKey = "my-secret-access-key";

        // Encrypt
        ByteBuffer encrypted = crypto.encrypt(
                credentialId,
                accessKey
        );

        System.out.println("Encrypted: " + encrypted);

        // Decrypt
        String decrypted = crypto.decrypt(
                credentialId,
                encrypted
        );

        System.out.println("Decrypted: " + decrypted);
        System.out.println("Matches: " + accessKey.equals(decrypted));
    }
}
