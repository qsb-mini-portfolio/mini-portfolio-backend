package com.qsportfolio.backend.domain.user;

import com.qsportfolio.backend.domain.transaction.Stock;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Data
@Table(name = "favorite_stock")
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteStock {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, name ="user_id")
    UUID userId;
    @Column(nullable = false, name = "stock_id")
    UUID stockId;
}
