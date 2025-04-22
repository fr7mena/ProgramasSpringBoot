package org.spring.examen_fabio_rama_mena.Service;

import org.spring.examen_fabio_rama_mena.Model.Habitacione;
import org.spring.examen_fabio_rama_mena.Model.HistorialReserva;
import org.spring.examen_fabio_rama_mena.Model.Reserva;
import org.spring.examen_fabio_rama_mena.Repository.HistorialReservaRepository;
import org.spring.examen_fabio_rama_mena.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {
    private ReservaRepository reservaRepository;
    private HistorialReservaService historialReservaService;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    //Metodos:

    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Reserva addReserva(Reserva reserva) {
        LocalDateTime fechaInicio = reserva.getFechaCheckin();
        LocalDateTime fechaFin = reserva.getFechaCheckout();
        List<Reserva> todasReservas = this.getAllReservas();
        String idHabitacion = reserva.getIdHabitacion().getIdHabitacion();
        boolean habilitado =false;
        for (Reserva re : todasReservas) {
            if (re.getIdHabitacion().getIdHabitacion().equals(idHabitacion)) {
                if ((fechaInicio.isBefore(re.getFechaCheckin()) && fechaInicio.isBefore(re.getFechaCheckout()))
                || (fechaInicio.isAfter(re.getFechaCheckout()))) {
                   habilitado = true;
                } else {
                    if (reserva.getBorrado() != null) {
                        habilitado = true;
                    }
                }
            } else {
                habilitado = true;
            }
        }
        if (!habilitado) {
            throw new RuntimeException("Reserva no se ha podido realizar");
        }
        return reservaRepository.save(reserva);
    }

    public Reserva updateBorradoCancelacionReserva(Reserva reserva) {
        if (reserva.getFechaCheckin().isBefore(LocalDateTime.now().minusDays(2))) {
            reserva.setBorrado("Ha sido cancelada la Reserva");
            reserva.setPrecio((float) 0);
            HistorialReserva hr = new HistorialReserva(reserva.getId(), "Cancelado");
            this.historialReservaService.addHistorial(hr);
            return this.reservaRepository.save(reserva);
        } else {
            reserva.setBorrado("Ha sido cancelada la Reserva, con penalización.");
            reserva.setPrecio((float)(reserva.getPrecio() * 0.2));
            HistorialReserva hr = new HistorialReserva(reserva.getId(), "Cancelado");
            this.historialReservaService.addHistorial(hr);
            return this.reservaRepository.save(reserva);
        }
    }
}
