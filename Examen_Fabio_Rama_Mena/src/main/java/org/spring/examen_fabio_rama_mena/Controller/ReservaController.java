package org.spring.examen_fabio_rama_mena.Controller;

import jakarta.validation.Valid;
import org.spring.examen_fabio_rama_mena.Model.Reserva;
import org.spring.examen_fabio_rama_mena.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@CacheConfig(cacheNames = "reservas")
public class ReservaController {
    private ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> addReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.addReserva(reserva));
    }
    @PutMapping("/Cancelacion")
    public ResponseEntity<Reserva> updateBorradoCancelacionReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.updateBorradoCancelacionReserva(reserva));
    }
}
