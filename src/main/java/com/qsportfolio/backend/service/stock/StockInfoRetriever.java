package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.stock.StockInfo;
import com.qsportfolio.backend.domain.stock.StockType;
import com.qsportfolio.backend.domain.transaction.Stock;

public interface StockInfoRetriever {
    StockInfo retrieveStockInformation(Stock stock);
}
