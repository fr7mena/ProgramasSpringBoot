package org.spring.examen_fabio_rama_mena.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "habitaciones")
public class Habitacione {
    @Id
    @Size(max = 10)
    @Column(name = "id_habitacion", nullable = false, length = 10)
    @Pattern(regexp = "^[P]{1}[0-9]{1}-[0-9]{2}-[ID]{1}$")
    private String idHabitacion;

    @Size(max = 50)
    @Column(name = "tipo", length = 50)
    @Size(max = 50, message = "La longitud máximas es 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s'-]+$", message = "El tipo solo acepta caracteres alfabéticos.")
    private String tipo;

    @Column(name = "planta")
    @Pattern(regexp = "^[0-9]$", message = "Solo acepta valores numéricos de un dígito")
    @PositiveOrZero
    private Integer planta;

    @Column(name = "numero")
    @Pattern(regexp = "^[0-9]{1,}$", message = "Solo se aceptan valores numéricos")
    @PositiveOrZero
    private Integer numero;

    @Size(max = 1)
    @Column(name = "ubicacion", length = 1)
    @Size(max = 1, message = "solo se acepta un único caracter.")
    @Pattern(regexp = "^[ID]$")
    private String ubicacion;

    @OneToMany(mappedBy = "idHabitacion")
    @JsonIgnoreProperties({"idHabitacion"})
    private Set<org.spring.examen_fabio_rama_mena.Model.Reserva> reservas = new LinkedHashSet<>();

    public String getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(String idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPlanta() {
        return planta;
    }

    public void setPlanta(Integer planta) {
        this.planta = planta;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Set<org.spring.examen_fabio_rama_mena.Model.Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<org.spring.examen_fabio_rama_mena.Model.Reserva> reservas) {
        this.reservas = reservas;
    }

}