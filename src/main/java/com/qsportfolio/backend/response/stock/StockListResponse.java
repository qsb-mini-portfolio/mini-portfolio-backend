package com.qsportfolio.backend.response.stock;

import com.qsportfolio.backend.response.ListResponse;
import com.qsportfolio.backend.response.transaction.TransactionResponse;

import java.util.List;

public class StockListResponse extends ListResponse<StockResponse> {
    public StockListResponse(List<StockResponse> items, int page, int totalElements) {
        super(items, page, totalElements);
    }
}
