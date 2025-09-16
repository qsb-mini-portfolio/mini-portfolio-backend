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
@Table(name = "stock_transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    UUID id;
    @Column(nullable = false, name = "stock_id")
    UUID stockId;
    @Column(nullable = false, name = "user_id")
    UUID userId;
    @Column(nullable = false)
    LocalDateTime date;
    @Column(nullable = false)
    float price;
    @Column(nullable = false)
    float volume;
}
