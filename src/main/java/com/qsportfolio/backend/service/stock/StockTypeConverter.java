package com.qsportfolio.backend.service.stock;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StockTypeConverter implements AttributeConverter<StockType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StockType stockType) {
        return stockType != null ? stockType.getId() : null;
    }

    @Override
    public StockType convertToEntityAttribute(Integer dbData) {
        return dbData != null ? StockType.fromId(dbData) : null;
    }
}
