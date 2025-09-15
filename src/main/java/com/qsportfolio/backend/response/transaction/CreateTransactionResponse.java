package com.qsportfolio.backend.response.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionResponse {
    UUID transactionId;
    UUID stockId;
    float price;
    float volume;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime date;
}
