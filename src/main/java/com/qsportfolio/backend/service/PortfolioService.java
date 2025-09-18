package com.qsportfolio.backend.service;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;
import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.errorHandler.AppException;
import com.qsportfolio.backend.repository.StockRepository;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.service.stock.StockPriceRetriever;
import com.qsportfolio.backend.service.stock.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;
    private final StockService stockService;

    public PortfolioService(
        TransactionRepository transactionRepository,
        StockService stockService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
    }

    public PortfolioDTO getPortfolio() {
        List<PortfolioByStockDTO> portfolioByStockDTOs = transactionRepository.getPortfolioGroupByStock();

        float portfolioPrice = 0;
        float boughtPortfolioPrice = 0;
        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            try {
                float price = stockService.getStockCurrentPrice(portfolioByStockDTO.getStock()) * portfolioByStockDTO.getVolume();
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
