package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.auth.RegisterRequest;
import com.qsportfolio.backend.request.transaction.CreateTransactionRequest;
import com.qsportfolio.backend.service.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public ResponseEntity<String> register(CreateTransactionRequest createTransactionRequest) {
        transactionService.createTransaction(
            createTransactionRequest.getStockId(),
            createTransactionRequest.getVolume(),
            createTransactionRequest.getPrice(),
            createTransactionRequest.getDate()
        );
        return ResponseEntity.ok("User registered successfully!");
    }
}
