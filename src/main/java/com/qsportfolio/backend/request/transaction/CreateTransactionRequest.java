package com.qsportfolio.backend.request.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateTransactionRequest {
    UUID stockId;
    float price;
    float volume;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime date;
}
