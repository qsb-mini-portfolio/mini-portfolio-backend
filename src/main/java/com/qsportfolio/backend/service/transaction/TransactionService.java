package com.qsportfolio.backend.service.transaction;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.repository.UserRepository;
import com.qsportfolio.backend.security.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository; // DO NO USE ONLY FOR DEMO PURPOSE !!!

    public TransactionService(
        TransactionRepository transactionRepository,
        StockRepository stockRepository,
        UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(
        UUID stockId,
        float price,
        float volume,
        LocalDateTime date) {

        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new AppException("Stock ID doesn't exist"));

        Transaction transaction = new Transaction();

        transaction.setStock(stock);
        transaction.setUserId(SecurityUtils.getCurrentUser().getId());
        transaction.setDate(date);
        transaction.setPrice(price);
        transaction.setVolume(volume);
        transaction.setDate(date);

        transactionRepository.save(transaction);
        return transaction;
    }

    public Page<Transaction> listTransaction(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findByUserId(SecurityUtils.getCurrentUser().getId(), pageable);
    }

    public void deleteTransactions(UUID userId, String stockSymbol) {
        Optional<Stock> stock = this.stockRepository.findBySymbol(stockSymbol);
        if (stock.isEmpty()) {
            throw new IllegalArgumentException("Stock with symbol " + stockSymbol + " not found");
        }
        UUID stockId = stock.get().getId();
        this.transactionRepository.deleteAll(this.transactionRepository.findAllByUserIdAndStockId(userId, stockId));
    }

    public void deleteTransaction(UUID transactionId) {
        this.transactionRepository.deleteById(transactionId);
    }

    ///
    /// FOR DEMO PURPOSE ONLY DO NOT USE
    ///
    ///
    @Transactional
    public void deleteAllTransactionForDemo() {
        transactionRepository.deleteByUserId(userRepository.findByUsername("demo").orElseThrow(() -> new AppException("Unable to find the demo user")).getId());
    }
}
