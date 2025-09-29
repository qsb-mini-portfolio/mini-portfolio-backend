package com.qsportfolio.backend.request.transaction;

import lombok.Data;

@Data
public class DeleteTransactionsRequest {
    String stockSymbol;
}
