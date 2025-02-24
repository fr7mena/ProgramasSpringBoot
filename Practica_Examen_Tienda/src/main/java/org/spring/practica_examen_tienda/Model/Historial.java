package org.spring.practica_examen_tienda.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historials"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "producto_id", nullable = false)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historials"})
    private org.spring.practica_examen_tienda.Model.Producto producto;

    @Column(name = "fecha_compra", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDate fechaCompra;

    @ColumnDefault("1")
    @Column(name = "cantidad")
    @PositiveOrZero
    private Integer cantidad;

    @Column(name = "tipo", length = 100) //Poner una expresion regular que solo permita que el tipo sea o "compra" o "devolucion"
    @NotNull
    @NotBlank
    @Size(max = 100, message = "El tipo no puede estar compuesto por más de 100 caracteres.")
    @Pattern(regexp = "^(compra|devolucion)$", message = "El tipo debe ser 'compra' o 'devolucion'")
    private String tipo;

    @Column(name = "descripcion", length = 200)
    @Size(max = 200, message = "La descripción no puede ocupar más de 200 caracteres.")
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public org.spring.practica_examen_tienda.Model.Producto getProducto() {
        return producto;
    }

    public void setProducto(org.spring.practica_examen_tienda.Model.Producto producto) {
        this.producto = producto;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}