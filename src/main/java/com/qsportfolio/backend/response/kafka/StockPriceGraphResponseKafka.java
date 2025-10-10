package com.qsportfolio.backend.response.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPriceGraphResponseKafka {
    String ticker;
    String period;
    String interval;
    List<Float> prices;
}
