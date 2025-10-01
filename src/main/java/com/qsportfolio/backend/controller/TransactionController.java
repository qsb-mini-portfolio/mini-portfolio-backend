package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.domain.user.User;
import com.qsportfolio.backend.request.transaction.UpdateTransactionByIdRequest;
import com.qsportfolio.backend.request.transaction.CreateTransactionRequest;
import com.qsportfolio.backend.request.transaction.DeleteTransactionRequest;
import com.qsportfolio.backend.request.transaction.DeleteTransactionsRequest;
import com.qsportfolio.backend.response.transaction.TransactionListResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponseFactory;
import com.qsportfolio.backend.service.transaction.TransactionImportService;
import com.qsportfolio.backend.service.transaction.TransactionService;
import com.qsportfolio.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionImportService transactionImportService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        Transaction transaction = transactionService.createTransaction(
            createTransactionRequest.getStockId(),
            createTransactionRequest.getPrice(),
            createTransactionRequest.getVolume(),
            createTransactionRequest.getDate()
        );

        return ResponseEntity.ok(TransactionResponseFactory.createTransactionResponse(transaction));
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TransactionImportService.Result importCsv(
        @RequestParam("file") MultipartFile file
        ) throws IOException {
        User user = userService.getUser();
        return transactionImportService.importTransactionCsv(user.getId(), file.getInputStream());
    }

    @GetMapping
    public ResponseEntity<TransactionListResponse> listTransaction(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Page<Transaction> pagination = transactionService.listTransaction(page, size);

        return ResponseEntity.ok(TransactionResponseFactory.createTransactionListResponse(pagination));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransactions(@RequestBody DeleteTransactionsRequest request){
        User user = userService.getUser();
        transactionService.deleteTransactions(user.getId(), request.getStockSymbol());
        return ResponseEntity.ok("Transaction(s) successfully removed !");

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTransaction(@PathVariable UUID id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(Map.of("message", "Transaction successfully removed !"));
    }

    @PutMapping
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody UpdateTransactionByIdRequest updateTransactionByIdRequest) {
        Transaction transaction = transactionService.updateTransaction(updateTransactionByIdRequest);
        return ResponseEntity.ok(TransactionResponseFactory.createTransactionResponse(transaction));
    }
}
