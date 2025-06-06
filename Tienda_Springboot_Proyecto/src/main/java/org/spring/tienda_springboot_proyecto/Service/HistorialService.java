package org.spring.tienda_springboot_proyecto.Service;

import org.spring.tienda_springboot_proyecto.Model.Historial;
import org.spring.tienda_springboot_proyecto.Model.Producto;
import org.spring.tienda_springboot_proyecto.Repository.ClienteRepository;
import org.spring.tienda_springboot_proyecto.Repository.HistorialRepository;
import org.spring.tienda_springboot_proyecto.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HistorialService {
    private HistorialRepository historialRepository;
    private ClienteService clienteService;
    private ProductoService productoService;

    @Autowired //Esto esta perfecto
    public HistorialService(HistorialRepository historialRepository, ClienteService clienteService, ProductoService productoService) {
        this.historialRepository = historialRepository;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    //Metodos replicados del CRUD que modificare para dar la funcionalidad correcta al programa y hacer VALIDACIONES

    public List<Historial> getAllHistoriales() {
        return this.historialRepository.findAll();
    } //Preguntar a victor como se trata en las listas

    public Historial getHistorialById(int id) { //Habra que preguntar a victor
        try{
            return this.historialRepository.findById(id).orElseThrow(); //Esta es una consulta que al ponerla aqui en el servicio se autoañade a la interfaz de ClienteRepository
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El historial no existe");
        }
    }

    public Historial addHistorial(Historial historial) { //Meto un tryCatch en la cracion y asignacion de un valor a producto????
        Producto producto = null;
        try {
            producto = this.productoService.getProductoById(historial.getProducto().getId());
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El producto no existe");
        }

        if (historial.getTipo().equalsIgnoreCase("compra")) {
            if (historial.getCantidad() < producto.getStock()) {
                producto.setStock(producto.getStock() - historial.getCantidad());
                this.productoService.addProducto(producto); //Actualizo el producto en la BD
                historial.setFechaCompra(LocalDate.now());
                this.historialRepository.save(historial); //Registro el historial en la BD
                return historial;
            } else {
                throw new RuntimeException("La cantidad a comprar es superior al stock del producto");
            }
        } else if (historial.getTipo().equalsIgnoreCase("devolucion")) {
            if ((historial.getFechaCompra().isBefore(LocalDate.now().plusDays(30)))) {
                producto.setStock(producto.getStock() + historial.getCantidad());
                this.productoService.addProducto(producto);
                historial.setFechaCompra(LocalDate.now());
                this.historialRepository.save(historial);
                return historial;
            } else {
                throw new RuntimeException("Han pasado más de 30 días desde que compraste le producto, lo siento, NO lo puedes devolver");
            }
        } else {
            throw new RuntimeException("El tipo de compra no existe");
        }
    }

    public Historial updateHistorial(Historial historial) {
        return this.historialRepository.save(historial);
    }

    public String deleteHistorial(int id) {
        Historial historial = this.historialRepository.findById(id).orElseThrow();
        this.historialRepository.delete(historial);
        return "El historial con id: " + id + " ha sido eliminado";
    }
}
