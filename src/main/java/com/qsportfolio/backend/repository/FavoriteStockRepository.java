package com.qsportfolio.backend.repository;

import com.qsportfolio.backend.domain.transaction.Stock;
import com.qsportfolio.backend.domain.user.FavoriteStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteStockRepository extends JpaRepository<FavoriteStock, UUID> {

    public FavoriteStock findByStockIdAndUserId(UUID stockId, UUID userId);

    public List<FavoriteStock> findByUserId(UUID userId);
}
