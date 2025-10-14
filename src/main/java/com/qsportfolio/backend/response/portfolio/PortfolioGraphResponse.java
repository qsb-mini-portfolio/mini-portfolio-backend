package com.qsportfolio.backend.response.portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioGraphResponse {
    String period;
    String interval;
    List<Float> prices;
}
