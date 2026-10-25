ALTER TABLE url_mapping
    ADD COLUMN idempotency_key VARCHAR(128) NULL,
    ADD CONSTRAINT uk_idempotency_key UNIQUE (idempotency_key);