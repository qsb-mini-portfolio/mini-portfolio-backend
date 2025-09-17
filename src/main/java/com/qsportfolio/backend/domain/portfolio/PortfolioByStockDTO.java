package com.qsportfolio.backend.domain.portfolio;

import com.qsportfolio.backend.domain.transaction.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioByStockDTO {
    Stock stock;
    float volume;
    float boughtPrice;
    Float currentPrice;
    Float yield;
    Float part;

    public PortfolioByStockDTO(Stock stock, double volume, double boughtPrice) {
        this.stock = stock;
        this.volume = (float)volume;
        this.boughtPrice = (float)boughtPrice;
    }
}
