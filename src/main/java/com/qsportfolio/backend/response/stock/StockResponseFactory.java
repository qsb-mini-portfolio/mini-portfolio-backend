package com.qsportfolio.backend.response.stock;

import com.qsportfolio.backend.domain.transaction.Stock;

import java.util.List;

public final class StockResponseFactory {
    public static StockResponse createStockResponse(Stock stock) {
        return new StockResponse(
            stock.getId(),
            stock.getSymbol(),
            stock.getName(),
            stock.getType().getDescription(),
            stock.getLastPrice()
        );
    }

    public static StockListResponse createStockListResponse(List<Stock> stocks) {
        List<StockResponse> stockResponseList = stocks.stream().map(StockResponseFactory::createStockResponse).toList();
        return new StockListResponse(
            stockResponseList,
            0,
            stockResponseList.size()
        );
    }
}
