package org.spring.trabajo_tienda_springboot.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "compras")
public class Compra {
    @EmbeddedId
    private CompraId id;

    @MapsId("usuarioId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuarios")
    private org.spring.trabajo_tienda_springboot.Model.Usuario usuario;

    @MapsId("productoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonBackReference("productos")
    private org.spring.trabajo_tienda_springboot.Model.Producto producto;

    @ColumnDefault("1")
    @Column(name = "cantidad")
    private Integer cantidad;

    public CompraId getId() {
        return id;
    }

    public void setId(CompraId id) {
        this.id = id;
    }

    public org.spring.trabajo_tienda_springboot.Model.Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(org.spring.trabajo_tienda_springboot.Model.Usuario usuario) {
        this.usuario = usuario;
    }

    public org.spring.trabajo_tienda_springboot.Model.Producto getProducto() {
        return producto;
    }

    public void setProducto(org.spring.trabajo_tienda_springboot.Model.Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}