CREATE TABLE stocks (
    id UUID PRIMARY KEY,
    symbol VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE Table stock_transaction (
    id UUID PRIMARY KEY,
    stock_id UUID NOT NULL REFERENCES stocks(id),
    user_id UUID NOT NULL REFERENCES users(id),
    date TIMESTAMP NOT NULL,
    price FLOAT4 NOT NULL,
    volume FLOAT4 NOT NULL
)