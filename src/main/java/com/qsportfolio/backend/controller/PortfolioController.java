package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.response.portfolio.PortfolioResponse;
import com.qsportfolio.backend.response.portfolio.PortfolioResponseFactory;
import com.qsportfolio.backend.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public ResponseEntity<PortfolioResponse> getPortfolio() {
        return ResponseEntity.ok(PortfolioResponseFactory.createPortfolioResponse(portfolioService.getPortfolio()));
    }
}
