package org.spring.tienda_springboot_proyecto.Repository;

import org.spring.tienda_springboot_proyecto.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
  /*public Producto findBy*/
}
