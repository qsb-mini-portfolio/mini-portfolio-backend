package com.qsportfolio.backend.controller;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.request.transaction.CreateStockRequest;
import com.qsportfolio.backend.response.kafka.StockPriceGraphResponseKafka;
import com.qsportfolio.backend.response.stock.StockListResponse;
import com.qsportfolio.backend.response.stock.StockPriceGraphResponse;
import com.qsportfolio.backend.response.stock.StockResponse;
import com.qsportfolio.backend.response.stock.StockResponseFactory;
import com.qsportfolio.backend.response.transaction.TransactionResponseFactory;
import com.qsportfolio.backend.service.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<StockResponse> getStock(@PathVariable String symbol) {
        return ResponseEntity.ok(
            StockResponseFactory.createStockResponse(
                stockService.getStockBySymbol(symbol)
            )
        );
    }

    @GetMapping
    public ResponseEntity<StockListResponse> getStocks() {
        return ResponseEntity.ok(
            StockResponseFactory.createStockListResponse(
                stockService.listAllStocks()
            )
        );
    }

    @PostMapping
    public ResponseEntity<StockResponse> createStock(@RequestBody CreateStockRequest createStockRequest) {
        Stock stock = stockService.createStock(
            createStockRequest.getSymbol(),
            createStockRequest.getName()
        );

        return ResponseEntity.ok(StockResponseFactory.createStockResponse(stock));
    }

    @GetMapping("/graph/{symbol}")
    public ResponseEntity<StockPriceGraphResponse> getStockPriceGraph(
        @PathVariable String symbol,
        @RequestParam(defaultValue = "5d") String period,
        @RequestParam(defaultValue = "15m") String interval) {
        return ResponseEntity.ok(
            StockResponseFactory.createStockPriceGraphResponse(
                stockService.getStockPriceGraph(symbol, period, interval)
            )
        );
    }

}
