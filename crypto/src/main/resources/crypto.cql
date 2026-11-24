CREATE TABLE meteo.encrypted_credentials (
    credential_id text PRIMARY KEY,
    ciphertext blob,
    key_version int,
    updated_at timestamp
);