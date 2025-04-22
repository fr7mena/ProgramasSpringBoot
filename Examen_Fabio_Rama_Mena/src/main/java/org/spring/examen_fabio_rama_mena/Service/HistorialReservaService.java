package org.spring.examen_fabio_rama_mena.Service;

import org.spring.examen_fabio_rama_mena.Model.HistorialReserva;
import org.spring.examen_fabio_rama_mena.Repository.HistorialReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HistorialReservaService {
    private HistorialReservaRepository historialReservaRepository;

    @Autowired
    public HistorialReservaService(HistorialReservaRepository historialReservaRepository) {
        this.historialReservaRepository = historialReservaRepository;
    }

    public HistorialReserva getHistorialReservaId(int id) {
        try {
            return historialReservaRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Historial reserva no encontrada");
        }
    }

    public HistorialReserva addHistorial(HistorialReserva historialReserva) {
        return historialReservaRepository.save(historialReserva);
    }
}
