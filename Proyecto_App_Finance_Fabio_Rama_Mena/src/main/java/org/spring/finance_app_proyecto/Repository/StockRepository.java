package org.spring.finance_app_proyecto.Repository;

import org.spring.finance_app_proyecto.Model.Stock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findByUserIdAndTickerAndPurchaseDate(Integer userId, String ticker, LocalDate purchaseDate);

    List<Stock> findByUserIdAndTicker(Integer userId, String ticker);

    List<Stock> findByUserIdAndPurchaseDate(Integer userId, LocalDate purchaseDate);

    List<Stock> findByUserId(Integer userId);

    List<Stock> findByUserIdOrderByPurchaseDateDesc(Integer userId, Pageable topTen);
}