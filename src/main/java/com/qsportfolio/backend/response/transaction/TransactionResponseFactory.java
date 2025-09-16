package com.qsportfolio.backend.response.transaction;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public final class TransactionResponseFactory {

    private TransactionResponseFactory() {}

    public static TransactionResponse createTransactionResponse(Transaction transaction) {
         return new TransactionResponse(
            transaction.getId(),
            transaction.getStockId(),
            transaction.getPrice(),
            transaction.getVolume(),
            transaction.getDate()
        );
    }

    public static TransactionListResponse createTransactionListResponse(Page<Transaction> transactionsPage) {
        List<TransactionResponse> transactionResponseList = transactionsPage
            .stream()
            .map(TransactionResponseFactory::createTransactionResponse)
            .toList();
        return new TransactionListResponse(
            transactionResponseList,
            transactionsPage.getNumber(),
            transactionsPage.getNumberOfElements());
    }

    public static StockResponse createStockResponse(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getSymbol(),
                stock.getName()
        );
    }
}
