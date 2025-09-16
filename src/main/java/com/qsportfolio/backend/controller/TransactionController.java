package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.request.transaction.CreateStockRequest;
import com.qsportfolio.backend.request.transaction.CreateTransactionRequest;
import com.qsportfolio.backend.response.transaction.CreateStockResponse;
import com.qsportfolio.backend.response.transaction.CreateTransactionResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponseFactory;
import com.qsportfolio.backend.service.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<CreateTransactionResponse> createTransaction(CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = transactionService.createTransaction(
            createTransactionRequest.getStockId(),
            createTransactionRequest.getVolume(),
            createTransactionRequest.getPrice(),
            createTransactionRequest.getDate()
        );

        return ResponseEntity.ok(TransactionResponseFactory.createTransactionResponse(transaction));
    }
    @PostMapping("/stock")
    public ResponseEntity<CreateStockResponse> createStock(CreateStockRequest createStockRequest) {
        Stock stock = transactionService.createStock(
                createStockRequest.getSymbol(),
                createStockRequest.getName()
        );

        return ResponseEntity.ok(TransactionResponseFactory.createStockResponse(stock));
    }

}
