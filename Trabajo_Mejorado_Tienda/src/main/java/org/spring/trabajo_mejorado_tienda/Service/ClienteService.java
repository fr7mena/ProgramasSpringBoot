package org.spring.trabajo_mejorado_tienda.Service;

import org.spring.tienda_springboot_proyecto.Model.Cliente;
import org.spring.tienda_springboot_proyecto.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Metodos del CRUD:

    //GetAll:
    public List<Cliente> getAllClientes() {
        return this.clienteRepository.findAll(); //Este metodo ya devuelve una lista
    }

    //GetCliente
    public Cliente getClienteById(int id) {
        try {
            return this.clienteRepository.findById(id).orElseThrow(); //Esta es una consulta que al ponerla aqui en el servicio se autoañade a la interfaz de ClienteRepository
        } catch (RuntimeException e) {
            throw new RuntimeException("El cliente no ha sido encontrado");
        }
    }

    //Insert Into Cliente:
    public Cliente addCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    //Update Cliente:
    public Cliente updateCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    //Delete Cliente:
    public String deleteCliente(int id) {
        Cliente cliente = this.clienteRepository.findById(id).orElseThrow();
        this.clienteRepository.delete(cliente);
        return "Cliente eliminado con id: " + cliente.getId();
    }
}
