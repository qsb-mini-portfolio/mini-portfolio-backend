package com.qsportfolio.backend.response.user;

import com.qsportfolio.backend.response.portfolio.PortfolioStockResponse;
import com.qsportfolio.backend.response.stock.StockResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteStockResponse {
    List<StockResponse> stocks;
}

