// File: src/main/java/org/spring/finance_app_proyecto/DTO/UserDashDTO.java
package org.spring.finance_app_proyecto.DTO;

import org.spring.finance_app_proyecto.Model.User;

import java.io.Serializable;

/**
 * DTO para transferir datos del usuario al dashboard.  Solo contiene los
 * atributos necesarios para mostrar en el dashboard.
 */
public class UserDashDTO implements Serializable { // Implementar Serializable es una buena práctica para DTOs
    private Integer id;
    private String name;
    private String email; // Aunque no lo uses en el js, puede ser útil para mostrarlo

    public UserDashDTO() {}

    public UserDashDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

