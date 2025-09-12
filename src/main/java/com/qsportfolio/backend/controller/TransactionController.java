package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.auth.RegisterRequest;
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
    public ResponseEntity<String> register() {
        transactionService.test();
        return ResponseEntity.ok("User registered successfully!");
    }
}
