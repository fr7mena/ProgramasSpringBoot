package org.spring.practica_examen_tienda.Repository;

import org.spring.practica_examen_tienda.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> getProductosByPrecioIsLessThan(int i);

    List<Producto> getProductosByPrecioIsGreaterThan(int i);

    List<Producto> getProductosByPrecioIsGreaterThanAndPrecioIsLessThan(int i, int i1);
}
