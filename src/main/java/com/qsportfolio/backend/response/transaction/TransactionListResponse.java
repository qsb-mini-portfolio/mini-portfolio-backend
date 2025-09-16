package com.qsportfolio.backend.response.transaction;

import com.qsportfolio.backend.response.ListResponse;

import java.util.List;

public class TransactionListResponse extends ListResponse<TransactionResponse> {
    public TransactionListResponse(List<TransactionResponse> items, int page, int totalElements) {
        super(items, page, totalElements);
    }
}
