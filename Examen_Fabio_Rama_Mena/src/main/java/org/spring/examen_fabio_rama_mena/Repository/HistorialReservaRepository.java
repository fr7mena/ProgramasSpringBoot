package org.spring.examen_fabio_rama_mena.Repository;

import org.spring.examen_fabio_rama_mena.Model.HistorialReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialReservaRepository extends JpaRepository<HistorialReserva, Integer> {


}
