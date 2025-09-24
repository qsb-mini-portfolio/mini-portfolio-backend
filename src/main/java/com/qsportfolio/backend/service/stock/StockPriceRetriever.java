package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.transaction.Stock;

import java.time.LocalDateTime;

public interface StockPriceRetriever {
    float retrievePriceForStock(Stock stock);
}
