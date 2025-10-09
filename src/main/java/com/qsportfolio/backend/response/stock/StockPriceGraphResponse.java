package com.qsportfolio.backend.response.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceGraphResponse {
    List<Float> prices;
}
