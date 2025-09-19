package com.qsportfolio.backend.response.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    List<SectorResponse> sectors;
    float currentPrice;
    float boughtPrice;
    float yield;
}
