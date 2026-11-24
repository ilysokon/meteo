package com.meteo.crypto;


import com.google.crypto.tink.Aead;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public final class CredentialCrypto {

    private final Aead aead;

    public CredentialCrypto(Aead aead) {
        this.aead = aead;
    }

    private byte[] aad(String credentialId) {
        return ("meteo:access-key:v1:" + credentialId)
                .getBytes(StandardCharsets.UTF_8);
    }

    public ByteBuffer encrypt(
            String credentialId,
            String accessKey) throws Exception {

        byte[] plaintext = accessKey.getBytes(
                StandardCharsets.UTF_8);

        byte[] ciphertext = aead.encrypt(
                plaintext, aad(credentialId));

        return ByteBuffer.wrap(ciphertext);
    }

    public String decrypt(
            String credentialId,
            ByteBuffer ciphertext) throws Exception {

        ByteBuffer copy = ciphertext.asReadOnlyBuffer();

        byte[] encrypted = new byte[copy.remaining()];
        copy.get(encrypted);

        byte[] plaintext = aead.decrypt(
                encrypted, aad(credentialId));

        return new String(
                plaintext, StandardCharsets.UTF_8);
    }
}
