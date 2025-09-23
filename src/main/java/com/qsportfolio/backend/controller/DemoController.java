package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.auth.LoginRequest;
import com.qsportfolio.backend.service.auth.AuthService;
import com.qsportfolio.backend.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Tag(name = "DemoController", description = "Controller for demo purpose. DO NOT USE OTHERWISE !!!")
public class DemoController {

    private final TransactionService transactionService;
    private final AuthService authService;

    public DemoController(TransactionService transactionService, AuthService authService) {
        this.transactionService = transactionService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<String> demoLogin() {
        try {
            authService.createUser("demo", "demoPassword", "demo@email");
        } catch (Exception e) {
            // Do nothing
        }
        String token = authService.login("demo", "demoPassword");
        transactionService.deleteAllTransactionForDemo();
        return ResponseEntity.ok(token);
    }
}
