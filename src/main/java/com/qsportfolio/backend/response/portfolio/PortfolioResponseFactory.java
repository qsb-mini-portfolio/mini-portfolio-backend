package com.qsportfolio.backend.response.portfolio;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;

import java.util.List;

public class PortfolioResponseFactory {

    public static PortfolioStockResponse createPortfolioStockResponse(PortfolioByStockDTO portfolioByStockDTO) {
        return new PortfolioStockResponse(
            portfolioByStockDTO.getStock().getSymbol(),
            portfolioByStockDTO.getStock().getName(),
            portfolioByStockDTO.getVolume(),
            portfolioByStockDTO.getBoughtPrice(),
            portfolioByStockDTO.getCurrentPrice(),
            portfolioByStockDTO.getYield(),
            portfolioByStockDTO.getPart()
        );
    }

    public static PortfolioResponse createPortfolioResponse(PortfolioDTO portfolioDTO) {
        List<PortfolioStockResponse> portfolioStockResponseList = portfolioDTO.getPortfolioByStockDTOs()
            .stream()
            .map(PortfolioResponseFactory::createPortfolioStockResponse)
            .toList();
        return new PortfolioResponse(
            portfolioStockResponseList,
            portfolioDTO.getCurrentPrice(),
            portfolioDTO.getBougthPrice(),
            portfolioDTO.getYield()
        );
    }
}
