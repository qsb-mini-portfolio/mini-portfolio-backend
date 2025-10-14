package com.qsportfolio.backend.request.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioGraphRequestKafka {
    String period;
    String interval;
    List<StockPositionRequestKafka> stocks;
    List<TransactionRequestKafka> transactions;
}
