// File: src/main/java/org/spring/finance_app_proyecto/Repository/TransactionRepository.java

package org.spring.finance_app_proyecto.Repository;

import org.spring.finance_app_proyecto.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserIdAndTypeAndCategoryAndTransactionDateBetween(
            Integer userId,
            String type,
            String category,
            LocalDate start,
            LocalDate end
    );

    List<Transaction> findByUserIdOrderByTransactionDateDesc(Integer userId, Pageable pageable);

    List<Transaction> findByUserIdAndTypeOrderByTransactionDateDesc(Integer userId, String type, Pageable pageable);

    List<Transaction> findByUserIdAndTypeAndTransactionDateBetween(Integer userId, String type, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByUserIdAndCategoryAndTransactionDateBetween(Integer userId, String category, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByUserIdAndTransactionDateBetween(Integer userId, LocalDate startDate, LocalDate endDate);
}


