package org.spring.finance_app_proyecto.Controller;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleRequestDTO;
import org.spring.finance_app_proyecto.DTO.StockSaleResponseDTO;
import org.spring.finance_app_proyecto.Service.SaleStockService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-sales")
public class SaleStockController {

    @Autowired
    private SaleStockService saleStockService; // Asegúrate de que el nombre del bean inyectado sea 'saleStockService'

    // Método auxiliar para obtener el ID de usuario de la sesión
    private Integer getUserIdFromSession(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        return userIdObj instanceof Integer ? (Integer) userIdObj : null;
    }

    // Endpoint para buscar acciones por ticker para vender (ya existente)
    @GetMapping("/find-by-ticker")
    public ResponseEntity<?> findStocksByTickerForSale(@RequestParam String ticker, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            List<StockCreateDTO> stocks = saleStockService.getStocksByTickerForSale(userId, ticker);
            if (stocks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "No se encontraron acciones con ese ticker o ya han sido vendidas por completo."));
            }
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al buscar acciones: " + e.getMessage()));
        }
    }

    // Endpoint para registrar la venta de una acción (ya existente)
    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody StockSaleRequestDTO requestDTO, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            StockSaleResponseDTO response = saleStockService.registerStockSale(requestDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al registrar la venta: " + e.getMessage()));
        }
    }

    // NUEVO ENDPOINT para obtener y filtrar el historial de ventas del usuario
    @GetMapping("/user-sales-history")
    public ResponseEntity<?> getUserSalesHistory(
            HttpSession session,
            @RequestParam(required = false) String ticker, // Parámetro opcional para el ticker
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sellDate) { // Parámetro opcional para la fecha de venta

        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }
        try {
            List<StockSaleResponseDTO> sales = saleStockService.getFilteredUserSales(userId, ticker, sellDate);
            if (sales.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No se encontraron ventas con los filtros aplicados."));
            }
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al obtener el historial de ventas: " + e.getMessage()));
        }
    }
}