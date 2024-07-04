CREATE TABLE IF NOT EXISTS wallet_user (
    id BIGSERIAL PRIMARY KEY,
    extern_id UUID NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT uc_first_last_name UNIQUE (first_name, last_name)
);

INSERT INTO wallet_user(extern_id, first_name, last_name, email, created_at) VALUES
('5d98b0af-1f21-4089-8321-2a552b083df0', 'mishal', 'alajmi', 'mishal.test@gmail.com', now());

CREATE INDEX IF NOT EXISTS created_at ON wallet_user (created_at);