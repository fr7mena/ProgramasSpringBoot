package org.spring.practica_examen_tienda.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    @NotBlank
    @Size(min = 1, max = 50, message = "El nombre no puede tener menos de 1 caracter y más de 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s'-]+$", message = "El nombre SOLO debe contener caracteres alfábeticos y/o espacios en blanco.")
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50, message = "El apellido no puede tener menos de 1 caracter y más de 50 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s'-]+$", message = "El apellido SOLO debe contener caracteres alfábeticos y/o espacios en blanco.")
    private String apellido;

    @Column(name = "nickname", nullable = false, length = 50)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50, message = "El nickname tiene que estar compuesto como mínimo por 1 caracter y como máximo por 50 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "El nickname solo acepta caracteres alfanuméricos,")
    private String nickname;

    @Column(name = "password", nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 8, max = 255, message = "La contraseña tiene que estar formada por un mínimo de 8 caracteres y un máximo de 255 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "La contraseña esta formada incorrectamente, debe tener mínimo una letra minúscula, otra mayúscula y un número.")
    private String password;

    @Column(name = "telefono", length = 15)
    @Size(min = 9, max = 9, message = "El teléfono debe tener exactamente 9 dígitos")
    @Pattern(regexp = "^[69]\\d{8}$", message = "El teléfono debe comenzar con 6 o 9 y tener un total de 9 dígitos")
    private String telefono;

    @Column(name = "domicilio", length = 100)
    @Size(min = 1, max = 100, message = "El domicilio debe de tener mínimo 1 caracter y máximo 100")
    private String domicilio;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnoreProperties({"cliente"}) // IMPORTANTISIMO PARA EVITAR LAS DEPENDENCIAS CIRCULARES, ADEMAS RECUERDO UN POCO SU FUNCIONAMINETO, SE PONE EL ATRIBUTO QUE QUIERO QUE SE IGNORE CUANDO LLAMO A UN ATRIBUTO CORRESPONDIENTE A OTRA CLASE, EN ESTE CASO YO LO QUE QUIERO ES QUE EN LA CLASE HISTORIAL NO SE ME LLAME A ESTE ATRIBUTO HISTORIALS DE LA CLASE CLIENTE, POR ELLO EN LA CLASE HISTORIAL LE DIRE QUE EL ATRIBUTO DE ESTA CLASE QUE NO QUIERO QUE SE ME MUESTRE SEA HISTORIALS
    private Set<org.spring.practica_examen_tienda.Model.Historial> historials = new LinkedHashSet<>();

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

    public Set<org.spring.practica_examen_tienda.Model.Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<org.spring.practica_examen_tienda.Model.Historial> historials) {
        this.historials = historials;
    }

}