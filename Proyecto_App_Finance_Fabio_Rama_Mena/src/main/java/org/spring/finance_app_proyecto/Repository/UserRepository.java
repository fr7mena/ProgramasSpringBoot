// File: src/main/java/org/spring/finance_app_proyecto/Repository/UserRepository.java

package org.spring.finance_app_proyecto.Repository;

import org.spring.finance_app_proyecto.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz para acceso a datos de la entidad User.
 * Spring implementa automáticamente los métodos de JpaRepository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

}