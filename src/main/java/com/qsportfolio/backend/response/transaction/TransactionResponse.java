package com.qsportfolio.backend.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qsportfolio.backend.response.stock.StockResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    UUID transactionId;
    StockResponse stock;
    float price;
    float volume;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime date;
}
