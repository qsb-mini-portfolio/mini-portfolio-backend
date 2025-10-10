package com.qsportfolio.backend.response.stock;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.response.kafka.StockPriceGraphResponseKafka;
import com.qsportfolio.backend.response.portfolio.PortfolioStockResponse;
import com.qsportfolio.backend.response.user.FavoriteStockResponse;

import java.util.ArrayList;
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

    public static FavoriteStockResponse createFavoriteStockResponse(List<Stock> stocks) {
        List<StockResponse> stockResponseList = stocks.stream().map(StockResponseFactory::createStockResponse).toList();
        return new FavoriteStockResponse(
                stockResponseList
        );
    }

    public static StockPriceGraphResponse createStockPriceGraphResponse(StockPriceGraphResponseKafka stockPriceGraphResponseKafka) {
        return new StockPriceGraphResponse(
            stockPriceGraphResponseKafka.getTicker(),
            stockPriceGraphResponseKafka.getPeriod(),
            stockPriceGraphResponseKafka.getInterval(),
            stockPriceGraphResponseKafka.getPrices());
    }
}
