package org.spring.trabajo_mejorado_tienda.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    @NotNull
    @NotBlank //Si pones @NotBlank ya no necesitas poner @NotEmpty porque la validacion @NotBlank ya incluye la validacion @NotEmpty
    @Size(min = 1, max = 50, message = "El nombre no debe superar los 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s'-]+$", message = "El nombre SOLO tiene que tener caracteres alfabéticos, también se acepta cualquier tipo de espaciado") //Nombres de clientes solo alfábeticos. ✅
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50, message = "El apellido no debe superar los 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s'-]+$", message = "El apellido SOLO puede tener caracteres alfabéticos, también se acepta cualquier tipo de espaciado") //Apellidos de clientes solo alfábeticos.✅
    private String apellido;

    @Column(name = "nickname", nullable = false, length = 50, unique = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50, message = "El nickname tiene que contener como mínimo 1 caracter y como máximo 50")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "El nickname solo acepta caracteres alphanuméricos")
    private String nickname;


    @Column(name = "password", nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 8, max = 50, message = "La password tiene que contener como mínimo 8 caracteres y como máximo 50")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "La password solo acepta caracteres alphanuméricos")
    private String password;

    @Column(name = "telefono", length = 15)
    @Size(min = 9, max = 9, message = "El número de teléfono debe de estar compuesto por 9 caracteres numéricos")
    @Pattern(regexp = "^[6,9]\\d{8}$", message = "El número de teléfono debe de estar compuesto únicamente por 9 caracteres numéricos y debe empezar por 6 o 9.") //Teléfono de cliente 9 dígitos que empiecen por 6 o 9. ✅
    private String telefono;

    @Column(name = "domicilio", length = 100)
    @Size(min = 1, max = 500, message = "La dirección del domicilio no debe de superar los 500 caracteres")
    private String domicilio;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties({"cliente"})
    private Set<org.spring.tienda_springboot_proyecto.Model.Historial> historials = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Set<org.spring.tienda_springboot_proyecto.Model.Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<org.spring.tienda_springboot_proyecto.Model.Historial> historials) {
        this.historials = historials;
    }

}