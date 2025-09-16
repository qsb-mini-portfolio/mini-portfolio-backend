package com.qsportfolio.backend.request.transaction;

import lombok.Data;

@Data
public class CreateStockRequest {
    String symbol;
    String name;
}

