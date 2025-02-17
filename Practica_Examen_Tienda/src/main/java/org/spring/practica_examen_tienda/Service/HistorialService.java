package org.spring.practica_examen_tienda.Service;

import org.spring.practica_examen_tienda.Model.Historial;
import org.spring.practica_examen_tienda.Model.Producto;
import org.spring.practica_examen_tienda.Repository.HistorialRepository;
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

    @Autowired
    public HistorialService(HistorialRepository historialRepository, ClienteService clienteService, ProductoService productoService) {
        this.historialRepository = historialRepository;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    //Metodos propios del CRUD:

    //GetAll:
    public List<Historial> getAllHistoriales() {
        return this.historialRepository.findAll();
    }

    //GetByid:
    public Historial getHistorialById(int id) {
        try {
            return this.historialRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El historial no existe");
        }
    }

    //Insert Into
    public Historial addHistorial(Historial historial) {
        Producto producto = null;
        try {
            producto = this.productoService.getProductoById(historial.getProducto().getId());
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El producto no existe");
        }
        if (historial.getTipo().equalsIgnoreCase("compra")) {
            if (historial.getCantidad() <= producto.getStock()) {
                producto.setStock(producto.getStock() - historial.getCantidad());
                this.productoService.updateProducto(producto);
                historial.setFechaCompra(LocalDate.now());
                return this.historialRepository.save(historial);
            } else {
                throw new RuntimeException("No puedes comprar una cantidad mayor que el propio stock del producto.");
            }
        } else if (historial.getTipo().equalsIgnoreCase("devolucion")) {
            if (historial.getFechaCompra().isBefore(LocalDate.now().plusDays(30))) { //Esto quiere decir que la fecha de la compra tiene que ser anterior a la fecha actual más 30 dias
                producto.setStock(producto.getStock() + historial.getCantidad());
                this.productoService.updateProducto(producto);
                historial.setFechaCompra(LocalDate.now());
                return this.historialRepository.save(historial);
            } else {
                throw new RuntimeException("La compra no se puede devolver porque han pasado 30 días tras su compra y, posterior a esa fecha, no se aceptan devoluciones.");
            }
        } else {
            throw new RuntimeException("No se puede procesar esa operación porque no se corresponde con una compra o una devolución.");
        }
    }

    //Update:
    public Historial updateHistorial(Historial historial) {
        return this.historialRepository.save(historial);
    }

    //DeleteById:
    public String deleteHistorial(int id) {
        Historial historial = this.getHistorialById(id);
        this.historialRepository.delete(historial);
        return "Historial eliminado con id: " + historial.getId() + ", eliminado correctamente.";
    }
}
