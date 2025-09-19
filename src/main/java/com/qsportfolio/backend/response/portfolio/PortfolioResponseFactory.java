package com.qsportfolio.backend.response.portfolio;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.portfolio.PortfolioDTO;
import com.qsportfolio.backend.service.stock.StockType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            portfolioDTO.getBoughtPrice(),
            portfolioDTO.getYield()
        );
    }

    public static List<SectorResponse> createSectorResponse(List<PortfolioByStockDTO> portfolioByStockDTO) {
        Map<StockType, Float> marketValues = new HashMap<>();
        Map<StockType, Float> volumes = new HashMap<>();

        for (PortfolioByStockDTO portfolioDTO : portfolioByStockDTO) {
            StockType type = portfolioDTO.getStock().getType();
            marketValues.put(type, marketValues.getOrDefault(type, 0.0f) +
                (portfolioDTO.getCurrentPrice() == null ? 0.0f : portfolioDTO.getCurrentPrice()));
            volumes.put(type, volumes.getOrDefault(type, 0.0f) + portfolioDTO.getVolume());
        }

        List<SectorResponse> result = new ArrayList<>();
        for (StockType type : marketValues.keySet()) {
            result.add(new SectorResponse(type.getDescription(), marketValues.get(type), volumes.get(type)));
        }
        return result;
    }

    public static DashboardResponse createDashboardResponse(PortfolioDTO portfolioDTO) {
        List<SectorResponse> sectorResponseList = createSectorResponse(portfolioDTO.getPortfolioByStockDTOs());
        return new DashboardResponse(
            sectorResponseList,
            portfolioDTO.getCurrentPrice(),
            portfolioDTO.getBoughtPrice(),
            portfolioDTO.getYield()
        );
    }
}
