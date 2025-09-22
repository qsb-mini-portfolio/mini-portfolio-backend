package com.qsportfolio.backend.service.stock;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.errorHandler.StockNotFoundException;
import com.qsportfolio.backend.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockPriceRetriever stockPriceRetriever;
    private final StockInfoRetriever stockInfoRetriever;

    public StockService(StockRepository stockRepository, StockPriceRetriever stockPriceRetriever, StockInfoRetriever stockInfoRetriever) {
        this.stockRepository = stockRepository;
        this.stockPriceRetriever = stockPriceRetriever;
        this.stockInfoRetriever = stockInfoRetriever;
    }

    public Stock createStock(String symbol, String name) {
        Optional<Stock> optionalStock = stockRepository.findFirstBySymbol(symbol);
        if (optionalStock.isPresent()) {
            return optionalStock.get();
        }

        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setName(name);
        stock.setType(StockType.OTHER);
        try {
            float lastPrice = stockPriceRetriever.retrievePriceForStock(stock);
            stock.setLastPrice(lastPrice);
            stock.setPriceDate(LocalDateTime.now());
        } catch (Exception e) {
            // Doesn't throw exception
        }

        try {
            StockType type = stockInfoRetriever.retrieveStockTypeInformation(stock);
            stock.setType(type);
        } catch (Exception e) {
            // Doesn't throw exception
        }

        stockRepository.save(stock);
        return stock;
    }

    public float getStockCurrentPrice(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol).orElseThrow(() -> new AppException("Stock Symbol doesn't exist"));
        return getStockCurrentPrice(stock);
    }

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findFirstBySymbol(symbol).orElseThrow(() -> new StockNotFoundException(symbol));
    }

    public float getStockCurrentPrice(Stock stock) {
        if (stock.getPriceDate() != null && stock.getPriceDate().toLocalDate().equals(LocalDate.now())) {
            return stock.getLastPrice();
        }

        float lastPrice = stockPriceRetriever.retrievePriceForStock(stock);

        stock.setLastPrice(lastPrice);
        stock.setPriceDate(LocalDateTime.now());
        stockRepository.save(stock);

        return stockPriceRetriever.retrievePriceForStock(stock);
    }

    public List<Stock> listAllStocks() {
        return stockRepository.findAll();
    }


}
