package org.spring.examen_fabio_rama_mena.Controller;

import jakarta.validation.Valid;
import org.spring.examen_fabio_rama_mena.Model.Habitacione;
import org.spring.examen_fabio_rama_mena.Service.HabitacioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitaciones")
@CacheConfig(cacheNames = "habitaciones")
public class HabitacioneController {
    private HabitacioneService habitacioneService;

    @Autowired
    public HabitacioneController(HabitacioneService habitacioneService) {
        this.habitacioneService = habitacioneService;
    }

    @GetMapping
    public ResponseEntity<List<Habitacione>> getAllHabitaciones() {
        return ResponseEntity.ok(habitacioneService.getAllHabitaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacione> getHabitacionById(@PathVariable String id) {
        return ResponseEntity.ok(this.habitacioneService.getHabitacioneById(id));
    }

    @PostMapping
    public ResponseEntity<Habitacione> addHabitacione(@Valid @RequestBody Habitacione habitacione) {
        return ResponseEntity.ok(habitacioneService.addHabitacione(habitacione));
    }
}
