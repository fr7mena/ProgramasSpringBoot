// File: src/main/java/org/spring/finance_app_proyecto/Controller/StockController.java
package org.spring.finance_app_proyecto.Controller;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.DTO.StockFilterDTO;
import org.spring.finance_app_proyecto.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    private Integer getUserIdFromSession(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        return userIdObj instanceof Integer ? (Integer) userIdObj : null;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStock(@RequestBody StockCreateDTO dto, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            StockCreateDTO createdStockDto = stockService.createStock(dto, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Acción creada correctamente",
                    "stockId", createdStockDto.getId() // Ahora obtenemos el ID directamente del DTO de respuesta
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/total-invested")
    public ResponseEntity<?> getTotalInvested(HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }
        BigDecimal totalInvested = stockService.getTotalInvested(userId);
        return ResponseEntity.ok(Map.of("totalInvested", totalInvested));
    }

    // Modificado para devolver List<StockCreateDTO>
    @GetMapping("/latest-stocks")
    public ResponseEntity<List<StockCreateDTO>> getLast10Stocks(HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // O un cuerpo de error si prefieres
        }
        List<StockCreateDTO> lastStocks = stockService.getLast10Stocks(userId);
        return ResponseEntity.ok(lastStocks);
    }

    // Modificado para devolver List<StockCreateDTO>
    @PostMapping("/filter-stocks")
    public ResponseEntity<List<StockCreateDTO>> getFilteredStocks(@RequestBody StockFilterDTO filterDTO, HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // O un cuerpo de error si prefieres
        }
        List<StockCreateDTO> filteredStocks = stockService.getFilteredStocks(userId, filterDTO);
        return ResponseEntity.ok(filteredStocks);
    }
}