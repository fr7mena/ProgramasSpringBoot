// File: src/main/java/org/spring/finance_app_proyecto/DTO/LoginDTO.java

package org.spring.finance_app_proyecto.DTO;

/**
 * DTO para datos de inicio de sesión.
 */
public class LoginDTO {
    private String name;
    private String password;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
