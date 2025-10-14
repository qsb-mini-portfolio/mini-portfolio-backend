package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.response.portfolio.DashboardResponse;
import com.qsportfolio.backend.response.portfolio.PortfolioGraphResponse;
import com.qsportfolio.backend.response.portfolio.PortfolioResponse;
import com.qsportfolio.backend.response.portfolio.PortfolioResponseFactory;
import com.qsportfolio.backend.service.portfolio.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(PortfolioResponseFactory.createDashboardResponse(portfolioService.getPortfolio()));
    }

    @GetMapping("/graph")
    public ResponseEntity<PortfolioGraphResponse> getPortfolioGraph(
        @RequestParam(defaultValue = "5d") String period,
        @RequestParam(defaultValue = "15m") String interval) {
        return ResponseEntity.ok(
            PortfolioResponseFactory.createPortfolioGraphResponse(
                portfolioService.getPortfolioGraph(period, interval)
            )
        );
    }
}
