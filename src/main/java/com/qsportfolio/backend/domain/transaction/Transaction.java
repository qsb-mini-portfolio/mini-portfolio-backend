package com.qsportfolio.backend.domain.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.qsportfolio.backend.domain.transaction.Stock;
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
    @ManyToOne
    @JoinColumn(name = "stock_id")
    Stock stock;
    @Column(nullable = false, name = "user_id")
    UUID userId;
    @Column(nullable = false)
    LocalDateTime date;
    @Column(nullable = false)
    float price;
    @Column(nullable = false)
    float volume;
}
