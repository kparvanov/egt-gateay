CREATE TABLE IF NOT EXISTS requests (
    request_id VARCHAR(36) PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    client VARCHAR(50) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    period INTEGER,
    service_name VARCHAR (20) NOT NULL,
    endpoint VARCHAR (50) NOT NULL
);