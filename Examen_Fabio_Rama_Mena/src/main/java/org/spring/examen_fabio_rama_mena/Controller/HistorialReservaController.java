package org.spring.examen_fabio_rama_mena.Controller;

import jakarta.validation.Valid;
import org.spring.examen_fabio_rama_mena.Model.HistorialReserva;
import org.spring.examen_fabio_rama_mena.Service.HistorialReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historiales")
@CacheConfig(cacheNames = "historiales")
public class HistorialReservaController {
    private HistorialReservaService historialReservaService;

    @Autowired
    public HistorialReservaController(HistorialReservaService historialReservaService) {
        this.historialReservaService = historialReservaService;
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<HistorialReserva> getHistorialReserva(@PathVariable int id) {
        return ResponseEntity.ok(this.historialReservaService.getHistorialReservaId(id));
    }

    @PostMapping
    public ResponseEntity<HistorialReserva> addHistorialReserva(@Valid @RequestBody HistorialReserva historialReserva) {
        return ResponseEntity.ok(historialReservaService.addHistorial(historialReserva));
    }
}
