package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.request.auth.LoginRequest;
import com.qsportfolio.backend.service.auth.AuthService;
import com.qsportfolio.backend.service.demo.DemoService;
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

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping
    public ResponseEntity<String> demoLogin() {
        return ResponseEntity.ok(demoService.demoLogin());
    }

    @GetMapping("/health")
    public ResponseEntity<String> pythonHealthCheck() {
        return ResponseEntity.ok(demoService.pythonHealthCheck());
    }
}
