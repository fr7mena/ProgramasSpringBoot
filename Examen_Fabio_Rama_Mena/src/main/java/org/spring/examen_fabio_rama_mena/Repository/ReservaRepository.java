package org.spring.examen_fabio_rama_mena.Repository;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.spring.examen_fabio_rama_mena.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
   /* *//*List<Reserva> findByFechaCheckinBetweenAndFechaCheckout(@NotNull @Future LocalDateTime fechaCheckinAfter, @NotNull @Future LocalDateTime fechaCheckinBefore, @NotNull @Future LocalDateTime fechaCheckout);*/
    /*List<Reserva> findByFechaCheckinBetween(@NotNull @Future LocalDateTime fechaCheckinAfter);*/
}
