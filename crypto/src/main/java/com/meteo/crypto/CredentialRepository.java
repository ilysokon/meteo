package com.meteo.crypto;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;

import java.nio.ByteBuffer;
import java.time.Instant;

public final class CredentialRepository {

    private final CqlSession session;
    private final CredentialCrypto crypto;
    private final PreparedStatement insert;
    private final PreparedStatement select;

    public CredentialRepository(
            CqlSession session,
            CredentialCrypto crypto) {

        this.session = session;
        this.crypto = crypto;

        insert = session.prepare("""
            INSERT INTO meteo.encrypted_credentials
            (credential_id, ciphertext,
             key_version, updated_at)
            VALUES (?, ?, ?, ?)
            """);

        select = session.prepare("""
            SELECT ciphertext
            FROM meteo.encrypted_credentials
            WHERE credential_id = ?
            """);
    }

    public void save(
            String credentialId,
            String accessKey) throws Exception {

        ByteBuffer encrypted =
                crypto.encrypt(credentialId, accessKey);

        session.execute(insert.bind(
                credentialId,
                encrypted,
                1,
                Instant.now()));
    }

    public String read(String credentialId)
            throws Exception {

        var row = session.execute(
                select.bind(credentialId)).one();

        if (row == null) {
            return null;
        }

        return crypto.decrypt(
                credentialId,
                row.getByteBuffer("ciphertext"));
    }
}

