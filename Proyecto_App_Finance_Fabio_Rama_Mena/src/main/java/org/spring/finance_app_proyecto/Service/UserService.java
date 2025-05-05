// File: src/main/java/org/spring/finance_app_proyecto/Service/UserService.java

package org.spring.finance_app_proyecto.Service;

import org.spring.finance_app_proyecto.Model.User;
import org.spring.finance_app_proyecto.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio que encapsula la lógica de negocio para usuarios.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}