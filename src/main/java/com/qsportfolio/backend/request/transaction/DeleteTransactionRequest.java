package com.qsportfolio.backend.request.transaction;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteTransactionRequest {
    UUID transactionId;
}
