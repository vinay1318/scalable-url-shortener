CREATE TABLE url_mapping (

    id BIGINT NOT NULL  AUTO_INCREMENT PRIMARY KEY,
    short_code VARCHAR(16) UNIQUE,
    long_url VARCHAR(2048) NOT NULL,
    expires_at DATETIME,
    created_at DATETIME,
    updated_at DATETIME
);