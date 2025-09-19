package com.qsportfolio.backend.response.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectorResponse {
    String sector;
    float currentPrice;
    float volume;
}
