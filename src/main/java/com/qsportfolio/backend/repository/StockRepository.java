package com.qsportfolio.backend.repository;

import com.qsportfolio.backend.domain.transaction.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
