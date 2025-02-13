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

@Service
public class HistorialService {
    private HistorialRepository historialRepository;
    private ClienteRepository clienteRepository;
    private ProductoRepository productoRepository;

    @Autowired //Esto no se si esta bien MAÑANA PREGUNTAR A KAMEL A VER SI LE GUSTA
    public HistorialService(HistorialRepository historialRepository, ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.historialRepository = historialRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    //Metodos replicados del CRUD que modificare para dar la funcionalidad correcta al programa y hacer VALIDACIONES

    public List<Historial> getAllHistoriales() {
        return this.historialRepository.findAll();
    }

    public Historial getHistorialById(int id) {
            return this.historialRepository.findById(id).orElseThrow(); //Esta es una consulta que al ponerla aqui en el servicio se autoañade a la interfaz de ClienteRepository
    }

    public Historial addHistorial(Historial historial) { //Meto un tryCatch en la cracion y asignacion de un valor a producto????
            Producto producto = this.productoRepository.findById(historial.getProducto().getId()).orElseThrow(()-> new RuntimeException("El producto no existe en la base de datos")); //Obtengo el producto para trabajar con el
        if (historial.getTipo().equalsIgnoreCase("compra")) {
            if (historial.getCantidad() < producto.getStock()) {
                producto.setStock(producto.getStock() - historial.getCantidad());
                this.productoRepository.save(producto); //Actualizo el producto en la BD
                historial.setFechaCompra(LocalDate.now());
                this.historialRepository.save(historial); //Registro el historial en la BD
                return historial;
            } else {
                throw new RuntimeException("La cantidad a comprar es superior al stock del producto");
            }
        }

        if (historial.getTipo().equalsIgnoreCase("devolucion")) {
            if ((historial.getFechaCompra().isBefore(LocalDate.now().plusDays(30)))) {
                producto.setStock(producto.getStock() + historial.getCantidad());
                this.productoRepository.save(producto);
                historial.setFechaCompra(LocalDate.now());
                this.historialRepository.save(historial);
                return historial;
            } else {
                throw new RuntimeException("Han pasado más de 30 días desde que compraste le producto, lo siento, NO lo puedes devolver");
            }
        }
        return null;
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
