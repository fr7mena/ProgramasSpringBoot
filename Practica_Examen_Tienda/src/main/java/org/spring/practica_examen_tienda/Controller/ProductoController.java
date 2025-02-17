package org.spring.practica_examen_tienda.Controller;

import jakarta.validation.Valid;
import org.spring.practica_examen_tienda.Model.Producto;
import org.spring.practica_examen_tienda.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CacheConfig(cacheNames = "productos")
public class ProductoController {

    private ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //Metedos propios del protocolo HTTP:

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable int id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.addProducto(producto));
    }

    @PutMapping
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.updateProducto(producto));
    }

    @PutMapping("/actualizar-descripcion")
    public ResponseEntity<List<Producto>> updateProductosDescripcionByPrecio() {
        return ResponseEntity.ok(this.productoService.updateProductosDescripcionByPrecio());
    }

    @PutMapping("/actualizar-descripcion-estandar")
    public ResponseEntity<List<Producto>> updateProductosDescripcionEstandarByPrecio() {
        return ResponseEntity.ok(this.productoService.updateProductosDescripcionByPrecioEstandar());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable int id) {
        return ResponseEntity.ok(productoService.deleteProducto(id));
    }
}
