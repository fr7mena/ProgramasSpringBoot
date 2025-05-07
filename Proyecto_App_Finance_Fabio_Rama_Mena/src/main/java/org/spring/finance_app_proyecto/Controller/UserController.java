// File: src/main/java/org/spring/finance_app_proyecto/Controller/UserController.java

package org.spring.finance_app_proyecto.Controller;

import jakarta.servlet.http.HttpSession;
import org.spring.finance_app_proyecto.DTO.LoginDTO;
import org.spring.finance_app_proyecto.DTO.UserDTO;
import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Esta clase gestiona las solicitudes REST relacionadas con usuarios.
 * Anotada con @RestController para enviar y recibir JSON automáticamente.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/users/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO, HttpSession session) {
        Optional<User> optionalUser = userService.findByName(loginDTO.getName());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nombre de usuario incorrecto"));
        }

        User user = optionalUser.get();

        if (!user.getPasswordHash().equals(loginDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Contraseña incorrecta"));
        }

        // 💡 Guardamos info útil en la sesión
        session.setAttribute("userId", user.getId()); // suponiendo que hay un getId()

        return ResponseEntity.ok(new UserDTO(user));
    }

    // POST /api/users/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        Optional<User> existingUser = userService.findByName(userDTO.getName());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Nombre de usuario ya existe"));
        }

        User savedUser = userService.save(userDTO.toEntity());
        return ResponseEntity.ok(new UserDTO(savedUser));
    }

    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No hay sesión activa"));
        }
        return ResponseEntity.ok(Map.of("userId", userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }

}
