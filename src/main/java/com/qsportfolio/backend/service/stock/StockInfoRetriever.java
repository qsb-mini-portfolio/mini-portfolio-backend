package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.transaction.Stock;

public interface StockInfoRetriever {
    StockType retrieveStockTypeInformation(Stock stock);
}
