package com.qsportfolio.backend.response.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioStockResponse {
    String symbol;
    String name;
    float volume;
    float boughtPrice;
    Float currentPrice;
    Float yield;
    Float part;
}
