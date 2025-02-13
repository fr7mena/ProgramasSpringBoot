package org.spring.trabajo_tienda_springboot.Repository;

import org.spring.trabajo_tienda_springboot.Model.Compra;
import org.spring.trabajo_tienda_springboot.Model.CompraId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends CrudRepository<Compra, CompraId> {

}
