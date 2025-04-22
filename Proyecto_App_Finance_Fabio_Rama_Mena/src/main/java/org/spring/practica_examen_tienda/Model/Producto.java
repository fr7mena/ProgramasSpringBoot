package org.spring.practica_examen_tienda.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; //No se pone el @NotNull, porque ya se ha gestionado solo al ser autoIncrement

    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 100, message = "El nombre del producto no puede superar los 100 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "El nombre del producto solo puede ser rellenado con caracteres alphanúmerico y/o espacios")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer stock;

    @OneToMany(mappedBy = "producto")
    @JsonIgnoreProperties({"producto"}) //IMPORTANTISIMO PONERLO PARA EVITAR DEPENDENCIAS CIRCULARES
    private Set<Historial> historials = new LinkedHashSet<>();

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
    }

}