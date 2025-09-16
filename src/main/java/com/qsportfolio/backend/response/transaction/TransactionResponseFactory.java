package com.qsportfolio.backend.response.transaction;

import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.domain.transaction.Stock;


public final class TransactionResponseFactory {

    private TransactionResponseFactory() {}

    public static CreateTransactionResponse createTransactionResponse(Transaction transaction) {
         return new CreateTransactionResponse(
            transaction.getId(),
            transaction.getStock_id(),
            transaction.getPrice(),
            transaction.getVolume(),
            transaction.getDate()
        );
    }

    public static CreateStockResponse createStockResponse(Stock stock) {
        return new CreateStockResponse(
                stock.getId(),
                stock.getSymbol(),
                stock.getName()
        );
    }
}
