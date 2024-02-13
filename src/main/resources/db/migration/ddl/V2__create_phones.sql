CREATE TABLE phones (
    id SERIAL PRIMARY KEY,
    manufacturer VARCHAR(255)  NOT NULL,
    model VARCHAR(255) NOT NULL,
    os VARCHAR(20) NOT NULL,
    os_version VARCHAR(20) NOT NULL,
    available BOOLEAN NOT NULL DEFAULT true
);