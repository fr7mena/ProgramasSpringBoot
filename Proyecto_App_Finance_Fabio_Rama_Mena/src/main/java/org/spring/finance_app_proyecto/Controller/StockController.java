package org.spring.finance_app_proyecto.Controller;

import org.spring.finance_app_proyecto.DTO.StockCreateDTO;
import org.spring.finance_app_proyecto.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/create")
    public ResponseEntity<?> createStock(@RequestBody StockCreateDTO dto, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        try {
            StockCreateDTO createdStockDto = stockService.createStock(dto, userId); // Llama al servicio para crear el Stock
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Acción creada correctamente",
                    "stockId", createdStockDto // Devuelve el DTO con el ID
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}