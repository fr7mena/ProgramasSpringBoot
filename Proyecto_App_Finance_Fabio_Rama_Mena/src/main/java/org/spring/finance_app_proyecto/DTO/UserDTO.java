// File: src/main/java/org/spring/finance_app_proyecto/DTO/UserDTO.java

package org.spring.finance_app_proyecto.DTO;

import org.spring.finance_app_proyecto.Model.User;

/**
 * DTO para transferencia de datos del usuario.
 */
public class UserDTO {
    private String name;
    private String email;
    private String password;

    public UserDTO() {}

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
    }

    public User toEntity() {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash(password);
        return u;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
