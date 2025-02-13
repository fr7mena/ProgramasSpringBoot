package org.spring.tienda_springboot_proyecto.Service;

import org.spring.tienda_springboot_proyecto.Model.Cliente;
import org.spring.tienda_springboot_proyecto.Model.Producto;
import org.spring.tienda_springboot_proyecto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Ojo hay que poner el @Service porque si no no funciona el @Autowired
public class ProductoService {
    private ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //Metodos del CRUD:

    //GetAll:
    public List<Producto> getAllProductos() {
        return this.productoRepository.findAll(); //Este metodo ya devuelve una lista
    }

    //Get
    public Producto getProductoById(Integer id) {
        try {
            return this.productoRepository.findById(id).orElseThrow(); //Esta es una consulta que al ponerla aqui en el servicio se autoañade a la interfaz de ClienteRepository
        } catch (RuntimeException e) {
            throw new RuntimeException("El producto no ha sido encontrado");
        }
    }

    //Insert Into:
    public Producto addProducto(Producto producto) {
        return this.productoRepository.save(producto);
    }

    //Update:
    public Producto updateProducto(Producto producto) {
        return this.productoRepository.save(producto);
    }

    //Delete:
    public String deleteProducto(int id) {
        Producto producto = this.productoRepository.findById(id).orElseThrow();
        this.productoRepository.delete(producto);
        return "Producto eliminado con id: " + producto.getId();
    }
}
