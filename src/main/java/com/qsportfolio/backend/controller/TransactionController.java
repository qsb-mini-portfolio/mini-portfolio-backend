package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.request.transaction.CreateStockRequest;
import com.qsportfolio.backend.request.transaction.CreateTransactionRequest;
import com.qsportfolio.backend.response.transaction.StockResponse;
import com.qsportfolio.backend.response.transaction.TransactionListResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponseFactory;
import com.qsportfolio.backend.service.transaction.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = transactionService.createTransaction(
            createTransactionRequest.getStockId(),
            createTransactionRequest.getVolume(),
            createTransactionRequest.getPrice(),
            createTransactionRequest.getDate()
        );

        return ResponseEntity.ok(TransactionResponseFactory.createTransactionResponse(transaction));
    }

    @PostMapping("/stock")
    public ResponseEntity<StockResponse> createStock(CreateStockRequest createStockRequest) {
        Stock stock = transactionService.createStock(
            createStockRequest.getSymbol(),
            createStockRequest.getName()
        );

        return ResponseEntity.ok(TransactionResponseFactory.createStockResponse(stock));
    }

    @GetMapping
    public ResponseEntity<TransactionListResponse> listTransaction(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Page<Transaction> pagination = transactionService.listTransaction(page, size);

        return ResponseEntity.ok(TransactionResponseFactory.createTransactionListResponse(pagination));
    }
}
