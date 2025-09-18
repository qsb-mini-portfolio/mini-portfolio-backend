package com.qsportfolio.backend.response.stock;

import com.qsportfolio.backend.domain.transaction.Stock;

public final class StockResponseFactory {
    public static StockResponse createStockResponse(Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getSymbol(),
            stock.getName(),
            stock.getType().getDescription()
        );
    }
}
