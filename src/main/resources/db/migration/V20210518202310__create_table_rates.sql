CREATE TABLE IF NOT EXISTS rates (
    id UUID PRIMARY KEY,
    currency VARCHAR(3) NOT NULL,
    rate NUMERIC NOT NULL,
    base VARCHAR(3) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

CREATE INDEX currency_timestamp_index ON rates(currency, timestamp);