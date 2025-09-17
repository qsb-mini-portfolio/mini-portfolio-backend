package com.qsportfolio.backend.domain.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    List<PortfolioByStockDTO> portfolioByStockDTOs;
    float currentPrice;
    float boughtPrice;
    float yield;
}
