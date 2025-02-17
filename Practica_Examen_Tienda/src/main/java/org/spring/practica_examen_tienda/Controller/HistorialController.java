package org.spring.practica_examen_tienda.Controller;

import jakarta.validation.Valid;
import org.spring.practica_examen_tienda.Model.Historial;
import org.spring.practica_examen_tienda.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiales")
@CacheConfig(cacheNames = "historiales")
public class HistorialController {
    private HistorialService historialService;

    @Autowired
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    //Metodos propios de HTTP

    @GetMapping
    public ResponseEntity<List<Historial>> getAllHistoriales() {
        return ResponseEntity.ok(historialService.getAllHistoriales());
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Historial> getHistorialById(@PathVariable int id) {
        return ResponseEntity.ok(historialService.getHistorialById(id));
    }

    @PostMapping
    public ResponseEntity<Historial> addHistorial(@Valid @RequestBody Historial historial) {
        return ResponseEntity.ok(historialService.addHistorial(historial));
    }

    @PutMapping
    public ResponseEntity<Historial> updateHistorial(@Valid @RequestBody Historial historial) {
        return ResponseEntity.ok(historialService.updateHistorial(historial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistorial(@PathVariable int id) {
        return ResponseEntity.ok(this.historialService.deleteHistorial(id));
    }

}
