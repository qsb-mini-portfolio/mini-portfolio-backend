package com.qsportfolio.backend.domain.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    UUID id;
    @Column(nullable = false)
    String symbol;
    @Column(nullable = false)
    String name;
    @Column(name = "price_date")
    LocalDateTime priceDate;
    @Column(name = "last_price")
    Float lastPrice;
}
