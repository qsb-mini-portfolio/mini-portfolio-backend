package com.qsportfolio.backend.service.stock.response;

import lombok.Data;

import java.util.List;

@Data
public class PolygonPriceResponse {
    String ticker;
    int queryCount;
    int resultsCount;
    boolean adjusted;
    List<Result> results;

    @Data
    public static class Result {
        long t;
        float o;
        float c;
        float h;
        float l;
        float vw;
        long v;
        int n;
    }
}