package com.qsportfolio.backend.domain.transaction;

import com.qsportfolio.backend.service.stock.StockType;
import com.qsportfolio.backend.service.stock.StockTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(nullable = false)
    String symbol;
    @Column(nullable = false)
    String name;
    @Column(name = "price_date")
    LocalDateTime priceDate;
    @Column(name = "last_price")
    Float lastPrice;
    @Column(nullable = false)
    @Convert(converter = StockTypeConverter.class)
    StockType type;
}
