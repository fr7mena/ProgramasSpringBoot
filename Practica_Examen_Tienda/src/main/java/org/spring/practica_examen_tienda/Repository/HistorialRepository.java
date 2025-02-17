package org.spring.practica_examen_tienda.Repository;

import org.spring.practica_examen_tienda.Model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Integer> {
}
