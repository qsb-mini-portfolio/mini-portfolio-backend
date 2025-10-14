package com.qsportfolio.backend.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockPositionRequestKafka {
    String symbol;
    float volume;
}
