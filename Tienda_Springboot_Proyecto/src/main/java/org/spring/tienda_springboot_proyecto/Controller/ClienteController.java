package org.spring.tienda_springboot_proyecto.Controller;


import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.spring.tienda_springboot_proyecto.Model.Cliente;
import org.spring.tienda_springboot_proyecto.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes") //Puedo poner en el parametro de la String el nombre que considere debido a que aqui solo marcas a donde tiene que acceder el cliente para obtener la informacion
@CacheConfig(cacheNames = "usuarios")
public class ClienteController {
    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Metodos propios del protocolo HTTP:

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(this.clienteService.getAllClientes());
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id) { //Tengo que mejorar estos metodos para tratar las excepciones!!!!!!!!!!!
        return ResponseEntity.ok(this.clienteService.getClienteById(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.addCliente(cliente));
    }

    @PutMapping
    public ResponseEntity<Cliente> updateCliente(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(this.clienteService.updateCliente(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable int id) {
        return ResponseEntity.ok(this.clienteService.deleteCliente(id));
    }
}

