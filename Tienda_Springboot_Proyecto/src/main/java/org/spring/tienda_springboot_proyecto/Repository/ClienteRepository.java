package org.spring.tienda_springboot_proyecto.Repository;

import org.spring.tienda_springboot_proyecto.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


}
