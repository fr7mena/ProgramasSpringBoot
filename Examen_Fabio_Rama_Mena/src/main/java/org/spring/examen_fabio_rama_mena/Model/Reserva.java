package org.spring.examen_fabio_rama_mena.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_habitacion", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reservas"})
    private Habitacione idHabitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reservas"})
    private org.spring.examen_fabio_rama_mena.Model.Usuario idUsuario;

    @NotNull
    @Column(name = "fecha_checkin", nullable = false)
    @Future
    private LocalDateTime fechaCheckin;

    @NotNull
    @Column(name = "fecha_checkout", nullable = false)
    @Future
    private LocalDateTime fechaCheckout;

    @Size(max = 20)
    @Column(name = "borrado", length = 20)
    private String borrado;

    @Column(name = "precio")
    private Float precio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Habitacione getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Habitacione idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public org.spring.examen_fabio_rama_mena.Model.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(org.spring.examen_fabio_rama_mena.Model.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaCheckin() {
        return fechaCheckin;
    }

    public void setFechaCheckin(LocalDateTime fechaCheckin) {
        this.fechaCheckin = fechaCheckin;
    }

    public LocalDateTime getFechaCheckout() {
        return fechaCheckout;
    }

    public void setFechaCheckout(LocalDateTime fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }

    public String getBorrado() {
        return borrado;
    }

    public void setBorrado(String borrado) {
        this.borrado = borrado;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

}