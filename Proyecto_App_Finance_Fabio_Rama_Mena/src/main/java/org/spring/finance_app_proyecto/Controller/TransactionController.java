package org.spring.finance_app_proyecto.Controller;

import jakarta.servlet.http.HttpSession;
import org.spring.finance_app_proyecto.DTO.TransactionCreateDTO;
import org.spring.finance_app_proyecto.DTO.TransactionFilterDTO;
import org.spring.finance_app_proyecto.Model.Transaction;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Service.TransactionService;
import org.spring.finance_app_proyecto.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateDTO dto, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        Integer userId = (Integer) userIdObj;
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        transactionService.createTransaction(dto, optionalUser.get());
        return ResponseEntity.ok(Map.of("message", "Transacción creada correctamente"));
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterTransactions(@RequestBody TransactionFilterDTO dto, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no autenticado"));
        }

        Integer userId = (Integer) userIdObj;
        List<Transaction> result = transactionService.findByFilter(userId, dto.getType(), dto.getCategory(), dto.getStartDate(), dto.getEndDate());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTransactions(@RequestBody List<Integer> ids) {
        transactionService.deleteByIds(ids);
        return ResponseEntity.ok(Map.of("message", "Transacciones eliminadas"));
    }
}