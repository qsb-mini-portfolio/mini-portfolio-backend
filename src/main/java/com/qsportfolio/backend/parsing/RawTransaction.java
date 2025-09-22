package com.qsportfolio.backend.parsing;

public record RawTransaction(
    String date, String type, String isin, String symbol, String name,
    String quantity, String price, String totalAmount, String fees,
    String currency, String fxRate, String withholdingTax, String notes
) {}