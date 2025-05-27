// File: src/main/java/org/spring/finance_app_proyecto/Controller/SaleStockController.java
package org.spring.finance_app_proyecto.Controller;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO; // Importar StockCreateDTO
import org.spring.finance_app_proyecto.DTO.StockSaleRequestDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleResponseDTO;
// import org.spring.finance_app_proyecto.DTO.StockToSellDTO; // ELIMINAR ESTA IMPORTACIÓN
import org.spring.finance_app_proyecto.Service.SaleStockService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-sales")
public class SaleStockController {

    @Autowired
    private SaleStockService stockSaleService;

    private Integer getUserIdFromSession(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        return userIdObj instanceof Integer ? (Integer) userIdObj : null;
    }

    // Endpoint para buscar acciones por ticker para vender
    @GetMapping("/find-by-ticker")
    public ResponseEntity<?> findStocksByTickerForSale(@RequestParam String ticker, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            List<StockCreateDTO> stocks = stockSaleService.getStocksByTickerForSale(userId, ticker); // Cambiar a StockCreateDTO
            if (stocks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No se encontraron acciones con ese ticker para vender o no tienes acciones disponibles."));
            }
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al buscar acciones: " + e.getMessage()));
        }
    }

    // Endpoint para registrar la venta de una acción
    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody StockSaleRequestDTO requestDTO, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            StockSaleResponseDTO response = stockSaleService.registerStockSale(requestDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al registrar la venta: " + e.getMessage()));
        }
    }
}