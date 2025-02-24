package org.spring.tienda_springboot_proyecto.Controller;

import jakarta.validation.Valid;
import org.spring.tienda_springboot_proyecto.Model.Producto;
import org.spring.tienda_springboot_proyecto.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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

    //Metodos propios del protocolo HTTP:

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
     return ResponseEntity.ok(this.productoService.getAllProductos());
    }

    @GetMapping("/{id}")
    /*@Cacheable("productos")*/
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(this.productoService.addProducto(producto));
    }

    @PutMapping
    public ResponseEntity<Producto> updateProducto(@Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(this.productoService.updateProducto(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@Valid @PathVariable int id) {
        return ResponseEntity.ok(this.productoService.deleteProducto(id));
    }

}