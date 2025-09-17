package com.qsportfolio.backend.service;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.service.stockPrice.StockPriceRetriever;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final StockPriceRetriever stockPriceRetriever;

    public PortfolioService(
        TransactionRepository transactionRepository,
        StockRepository stockRepository,
        StockPriceRetriever stockPriceRetriever) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
        this.stockPriceRetriever = stockPriceRetriever;
    }

    public float getStockCurrentPrice(String symbol) {
        Stock stock = stockRepository.findFirstBySymbol(symbol).orElseThrow(() -> new AppException("Stock Symbol doesn't exist"));
        return getStockCurrentPrice(stock);
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

    public PortfolioDTO getPortfolio() {
        List<PortfolioByStockDTO> portfolioByStockDTOs = transactionRepository.getPortfolioGroupByStock();

        float portfolioPrice = 0;
        float boughtPortfolioPrice = 0;
        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            try {
                float price = getStockCurrentPrice(portfolioByStockDTO.getStock());
                portfolioPrice += price;
                boughtPortfolioPrice += portfolioByStockDTO.getBoughtPrice();
                portfolioByStockDTO.setCurrentPrice(price);
                portfolioByStockDTO.setYield((price - portfolioByStockDTO.getBoughtPrice()) / portfolioByStockDTO.getBoughtPrice());
            } catch (Exception e) {
                portfolioByStockDTO.setCurrentPrice(null);
                portfolioByStockDTO.setYield(null);
            }
        }

        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            if (portfolioByStockDTO.getCurrentPrice() != null) {
                portfolioByStockDTO.setPart(portfolioByStockDTO.getCurrentPrice() / portfolioPrice);
            } else {
                portfolioByStockDTO.setPart(null);
            }
        }

        return new PortfolioDTO(
            portfolioByStockDTOs,
            portfolioPrice,
            boughtPortfolioPrice,
            (portfolioPrice - boughtPortfolioPrice) / boughtPortfolioPrice
        );
    }
}
