package com.qsportfolio.backend.service.transaction;

import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.security.SecurityUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
