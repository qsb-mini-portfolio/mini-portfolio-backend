package com.qsportfolio.backend.repository;

import com.qsportfolio.backend.domain.transaction.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Page<Transaction> findByUserId(UUID userId, Pageable pageable);

}
