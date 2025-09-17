package com.qsportfolio.backend.repository;

import com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO;
import com.qsportfolio.backend.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    public Page<Transaction> findByUserId(UUID userId, Pageable pageable);

    @Query("SELECT new com.qsportfolio.backend.domain.portfolio.PortfolioByStockDTO(t.stock, SUM(t.volume), SUM(t.volume * t.price)) " +
        "FROM Transaction t " +
        "JOIN t.stock s " +
        "GROUP BY t.stock")
    public List<PortfolioByStockDTO> getPortfolioGroupByStock();
}
