package com.qsportfolio.backend.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
public class CreateStockResponse {
    UUID stockId;
    String symbol;
    String name;
}

