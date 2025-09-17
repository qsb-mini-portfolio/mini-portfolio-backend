package com.qsportfolio.backend.repository;

import com.qsportfolio.backend.domain.transaction.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    Optional<Stock> findFirstBySymbol(String symbol);

}
