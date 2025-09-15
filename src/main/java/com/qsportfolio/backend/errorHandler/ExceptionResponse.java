package com.qsportfolio.backend.errorHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    int status;
    String message;
    LocalDateTime timestamp;
}
