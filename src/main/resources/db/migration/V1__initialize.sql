CREATE TABLE IF NOT EXISTS wallet_user (
    id BIGSERIAL PRIMARY KEY,
    extern_id UUID NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS created_at ON wallet_user (created_at);