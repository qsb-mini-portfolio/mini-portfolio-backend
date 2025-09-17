package com.qsportfolio.backend.service.stockPrice;

import lombok.Data;

import java.util.List;

@Data
public class PolygonResponse {
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