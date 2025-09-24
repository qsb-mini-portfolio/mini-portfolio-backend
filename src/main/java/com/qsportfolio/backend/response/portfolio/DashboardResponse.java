package com.qsportfolio.backend.response.portfolio;

import com.qsportfolio.backend.response.stock.StockResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    List<SectorResponse> sectors;
    PortfolioResponse portfolio;
    float currentPrice;
    float boughtPrice;
    float yield;
}
