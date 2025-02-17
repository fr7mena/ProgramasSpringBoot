package org.spring.practica_examen_tienda.Service;

import org.spring.practica_examen_tienda.Model.Cliente;
import org.spring.practica_examen_tienda.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Metodos del CRUD con sus respectivas validaciones:

    //GetAll:
    public List<Cliente> getAllClientes() {
        return this.clienteRepository.findAll();
    }

    //GetByid:
    public Cliente getClienteById(int id) {
        try {
            return this.clienteRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("El cliente no existe");
        }
    }

    //Insert Into
    public Cliente addCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    //Update:
    public Cliente updateCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    //DeleteById:
    public String deleteCliente(int id) {
        Cliente cliente = this.getClienteById(id);
        this.clienteRepository.delete(cliente);
        return "Cliente eliminado con id: " + cliente.getId() + ", eliminado correctamente.";
    }
}
