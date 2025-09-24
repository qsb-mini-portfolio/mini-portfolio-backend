CREATE TABLE favorite_stock (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stock_id UUID NOT NULL REFERENCES stocks(id),
    user_id UUID NOT NULL REFERENCES users(id),
    UNIQUE(stock_id, user_id)
)