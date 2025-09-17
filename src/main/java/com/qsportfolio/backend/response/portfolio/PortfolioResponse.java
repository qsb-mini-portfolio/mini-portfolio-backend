package com.qsportfolio.backend.response.portfolio;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResponse {
    List<PortfolioStockResponse> stocks;
    float currentPrice;
    float boughtPrice;
    float yield;
}
