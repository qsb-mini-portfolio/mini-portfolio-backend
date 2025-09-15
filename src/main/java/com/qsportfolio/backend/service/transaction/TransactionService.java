package com.qsportfolio.backend.service.transaction;

import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

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

        return transaction;
    }
}
