package com.qsportfolio.backend.response.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    UUID stockId;
    String symbol;
    String name;
}

