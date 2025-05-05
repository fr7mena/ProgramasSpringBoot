package org.spring.finance_app_proyecto.Repository;

import org.spring.finance_app_proyecto.Model.StockPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryStockRepository extends JpaRepository<StockPriceHistory, Integer> {

}
