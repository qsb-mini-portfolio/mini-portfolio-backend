package com.qsportfolio.backend.response.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    List<PortfolioStockResponse> stocks;
    float currentPrice;
    float boughtPrice;
    float yield;
}
