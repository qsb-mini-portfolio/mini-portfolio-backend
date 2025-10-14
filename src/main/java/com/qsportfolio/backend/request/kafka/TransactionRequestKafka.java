package com.qsportfolio.backend.request.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestKafka {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime date;
    String symbol;
    float volume;
}
