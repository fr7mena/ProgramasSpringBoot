package org.spring.practica_examen_tienda.Repository;

import org.spring.practica_examen_tienda.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //RECUERDA PONER REPOSITORY QUE ES UNA MUY BUENA PRACTICA Y AUNQUE NO HAYA ERRORES SI NO LO PONES, DEBES PONERLO POR TEMAS DE ESTANDARIZACION.
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
