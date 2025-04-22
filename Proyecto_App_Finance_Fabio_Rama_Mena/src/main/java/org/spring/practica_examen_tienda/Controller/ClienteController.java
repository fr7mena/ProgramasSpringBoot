package org.spring.practica_examen_tienda.Controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.spring.practica_examen_tienda.Model.Cliente;
import org.spring.practica_examen_tienda.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CacheConfig(cacheNames = "clientes")
public class ClienteController {
    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Metodos propios del HTTP para conseguir tratar la conexion entre cliente y servidor:

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(this.clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    @Cacheable
    public  ResponseEntity<Cliente> getClienteById(@PathVariable int id) {
        return ResponseEntity.ok(this.clienteService.getClienteById(id));
    }

    @PostMapping
    public  ResponseEntity<Cliente> addCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.addCliente(cliente));
    }

    @PutMapping
    public  ResponseEntity<Cliente> updateCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.updateCliente(cliente));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCliente(@PathVariable int id) {
        return ResponseEntity.ok(this.clienteService.deleteCliente(id));
    }
}
