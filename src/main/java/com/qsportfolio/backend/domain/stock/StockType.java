package com.qsportfolio.backend.domain.stock;

import lombok.Getter;

@Getter
public enum StockType {
    AGRICULTURE(0, 1, 9, "Agriculture"),
    MINING(1, 10, 14, "Mining"),
    CONSTRUCTION(2, 15, 17, "Construction"),
    MANUFACTURING(3, 20, 39, "Manufacturing"),
    TRANSPORTATION(4, 40, 49, "Transportation"),
    WHOLESALE(5, 50, 51, "Wholesale trade"),
    RETAIL(6, 52, 59, "Retail Trade"),
    FINANCE(7, 60, 67, "Finance, Insurance"),
    SERVICE(8, 70, 89, "Services"),
    ADMINISTRATION(9, 91, 99, "Public Administration"),
    OTHER(10, -1, -1, "Other");

    private final int id;
    private final int low;
    private final int high;
    private final String description;

    StockType(int id, int low, int high, String description) {
        this.id = id;
        this.low = low;
        this.high = high;
        this.description = description;
    }

    public static StockType fromId(int id) {
        for (StockType e : values()) {
            if (e.id == id) {
                return e;
            }
        }
        throw new IllegalArgumentException("No enum constant with id " + id);
    }

    public static StockType fromSicCode(int sicCode) {
        int sicPrefix = sicCode / 100; // take first 2 digits
        for (StockType type : values()) {
            if (sicPrefix >= type.low && sicPrefix <= type.high) {
                return type;
            }
        }
        return OTHER;
    }
}
