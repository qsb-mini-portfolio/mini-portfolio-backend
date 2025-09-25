package com.qsportfolio.backend.service;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;
import com.qsportfolio.backend.repository.TransactionRepository;
import com.qsportfolio.backend.security.SecurityUtils;
import com.qsportfolio.backend.service.stock.StockService;
import org.springframework.stereotype.Service;

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
        List<PortfolioByStockDTO> portfolioByStockDTOs = transactionRepository.getPortfolioGroupByStockFromUser(SecurityUtils.getCurrentUser().getId());

        float portfolioPrice = 0;
        float boughtPortfolioPrice = 0;
        for (PortfolioByStockDTO portfolioByStockDTO : portfolioByStockDTOs) {
            stockService.updateStockPrice(portfolioByStockDTO.getStock());
            if (portfolioByStockDTO.getStock().getLastPrice() != null) {
                portfolioByStockDTO.setCurrentPrice(portfolioByStockDTO.getStock().getLastPrice());
                portfolioByStockDTO.setYield((portfolioByStockDTO.getCurrentPrice() - portfolioByStockDTO.getBoughtPrice()) / portfolioByStockDTO.getBoughtPrice());
                portfolioPrice += portfolioByStockDTO.getCurrentPrice();
                boughtPortfolioPrice += portfolioByStockDTO.getBoughtPrice();
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
