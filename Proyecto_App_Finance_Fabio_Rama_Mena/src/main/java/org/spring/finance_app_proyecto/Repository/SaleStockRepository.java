package org.spring.finance_app_proyecto.Repository;

import org.spring.finance_app_proyecto.Model.StockSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleStockRepository extends JpaRepository<StockSale, Integer> {

    // Recupera las últimas 10 ventas de un usuario, ordenadas por fecha de venta y creación.
    // La relación con el usuario se establece a través de la entidad Stock (ss.stock.user.id).
    @Query("SELECT ss FROM StockSale ss JOIN ss.stock s WHERE s.user.id = :userId ORDER BY ss.sellDate DESC, ss.createdAt DESC")
    List<StockSale> findTop10ByUserIdOrderBySellDateDescCreatedAtDesc(@Param("userId") Integer userId);

    // Busca ventas por el ID de usuario y un fragmento de ticker, insensible a mayúsculas/minúsculas.
    @Query("SELECT ss FROM StockSale ss JOIN ss.stock s WHERE s.user.id = :userId AND LOWER(s.ticker) LIKE LOWER(CONCAT('%', :ticker, '%')) ORDER BY ss.sellDate DESC, ss.createdAt DESC")
    List<StockSale> findByUserIdAndStockTickerContainingIgnoreCase(@Param("userId") Integer userId, @Param("ticker") String ticker);

    // Busca ventas por el ID de usuario y una fecha de venta específica.
    @Query("SELECT ss FROM StockSale ss JOIN ss.stock s WHERE s.user.id = :userId AND ss.sellDate = :sellDate ORDER BY ss.sellDate DESC, ss.createdAt DESC")
    List<StockSale> findByUserIdAndSellDate(@Param("userId") Integer userId, @Param("sellDate") LocalDate sellDate);

    // Busca ventas por el ID de usuario, un fragmento de ticker y una fecha de venta específica.
    @Query("SELECT ss FROM StockSale ss JOIN ss.stock s WHERE s.user.id = :userId AND LOWER(s.ticker) LIKE LOWER(CONCAT('%', :ticker, '%')) AND ss.sellDate = :sellDate ORDER BY ss.sellDate DESC, ss.createdAt DESC")
    List<StockSale> findByUserIdAndStockTickerContainingIgnoreCaseAndSellDate(@Param("userId") Integer userId, @Param("ticker") String ticker, @Param("sellDate") LocalDate sellDate);
}