package org.spring.tienda_springboot_proyecto.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    /*@JsonBackReference("cliente")*/
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historials"})
    @NotNull
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "producto_id", nullable = false)
    /*@JsonBackReference("producto")*/
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historials"})
    @NotNull
    private org.spring.tienda_springboot_proyecto.Model.Producto producto;

    @Column(name = "fecha_compra", nullable = false)
    @NotNull
    private LocalDate fechaCompra;

    @ColumnDefault("1")
    @Column(name = "cantidad")
    @PositiveOrZero
    private Integer cantidad;

    @Column(name = "tipo", length = 100)
    private String tipo;

    @Column(name = "descripcion", length = 200)
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

    public org.spring.tienda_springboot_proyecto.Model.Producto getProducto() {
        return producto;
    }

    public void setProducto(org.spring.tienda_springboot_proyecto.Model.Producto producto) {
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