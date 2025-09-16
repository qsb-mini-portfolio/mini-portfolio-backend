package com.qsportfolio.backend.service.transaction;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.security.SecurityUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;

    public TransactionService(TransactionRepository transactionRepository, StockRepository stockRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
    }

    public Transaction createTransaction(
        UUID stockId,
        float price,
        float volume,
        LocalDateTime date) {

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            stockId,
            SecurityUtils.getCurrentUser().getId(),
            date,
            price,
            volume
        );

        try {
            transactionRepository.save(transaction);
        } catch (DataIntegrityViolationException e) {
            throw new AppException("Stock ID doesn't exist");
        }
        return transaction;
    }

    public Page<Transaction> listTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findByUserId(SecurityUtils.getCurrentUser().getId(), pageable);
    }

    public Stock createStock(String symbol, String name) {
        Stock stock = new Stock(
                UUID.randomUUID(),
                symbol,
                name
        );
        stockRepository.save(stock);
        return stock;
    }
}
