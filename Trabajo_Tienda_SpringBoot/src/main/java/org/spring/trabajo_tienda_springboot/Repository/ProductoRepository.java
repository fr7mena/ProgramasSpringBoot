package org.spring.trabajo_tienda_springboot.Repository;

import org.spring.trabajo_tienda_springboot.Model.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Integer> {

}
