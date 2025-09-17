package com.qsportfolio.backend.errorHandler;

public class StockNotFoundException extends RuntimeException {
    private final String key;

    public StockNotFoundException(String key) {
        super("Stock not found for key: " + key);
        this.key = key;
    }

    public String getKey() { return key; }
}
