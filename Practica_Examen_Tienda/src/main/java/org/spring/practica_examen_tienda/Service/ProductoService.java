package org.spring.practica_examen_tienda.Service;

import org.spring.practica_examen_tienda.Model.Producto;
import org.spring.practica_examen_tienda.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoService {
    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Metodos del Crud de Producto:

    //GetAll:
    public List<Producto> getAllProductos() {
        return this.productoRepository.findAll();
    }

    //GetByid:
    public Producto getProductoById(int id) {
        try {
            return this.productoRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El cliente no existe");
        }
    }

    //Insert Into
    public Producto addProducto(Producto producto) {
        Double precio = producto.getPrecio().doubleValue();
        if (precio < 10) {
            producto.setDescripcion(producto.getDescripcion() + ", ¡PRODUCTO EN OFERTA!");
        }

        if (precio > 200) {
            producto.setDescripcion(producto.getDescripcion() + ", ¡PRODUCTO DE CALIDAD!");
        }
        return this.productoRepository.save(producto);
    }

    //Update:
    public Producto updateProducto(Producto producto) {
        return this.productoRepository.save(producto);
    }

    //Update solo los productos que tengas un precio entre 20 y 50 euros a productos de calidad estandar:
    public List<Producto> updateProductosDescripcionByPrecio() {
        List<Producto> productosOferta = this.productoRepository.getProductosByPrecioIsLessThan(10);
        List<Producto> productosCalidad = this.productoRepository.getProductosByPrecioIsGreaterThan(200);
        List<Producto> productosActualizados = new ArrayList<>();

        for (Producto producto : productosOferta) {
            producto.setDescripcion(producto.getDescripcion() + ", ¡PRODUCTO EN OFERTA!");
            this.productoRepository.save(producto);
            productosActualizados.add(producto);
        }
        for (Producto producto : productosCalidad) {
            producto.setDescripcion(producto.getDescripcion() + ", ¡PRODUCTO DE CALIDAD!");
            this.productoRepository.save(producto);
            productosActualizados.add(producto);
        }
        return productosActualizados;
    }

    //Update para poner el precio de los productos que son normales;
    public List<Producto> updateProductosDescripcionByPrecioEstandar() {
        List<Producto> productosActualizados = new ArrayList<>();
        List<Producto> productosEstandar = this.productoRepository.getProductosByPrecioIsGreaterThanAndPrecioIsLessThan(10, 200);
        for (Producto producto : productosEstandar) {
            if (!producto.getDescripcion().contains("¡PRODUCTO DE GAMA MEDIA!")) {
                producto.setDescripcion(producto.getDescripcion() + ", ¡PRODUCTO DE GAMA MEDIA!");
                this.productoRepository.save(producto);
                productosActualizados.add(producto);
            }
        }
        return productosActualizados;
    }

    //DeleteById:
    public String deleteProducto(int id) {
        Producto producto = this.getProductoById(id);
        this.productoRepository.delete(producto);
        return "Producto eliminado con id: " + producto.getId() + ", eliminado correctamente.";
    }

}
