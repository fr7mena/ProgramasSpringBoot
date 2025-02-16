package org.spring.trabajo_mejorado_tienda.Controller;

import org.spring.tienda_mejorado_tienda.Model.Historial;
import org.spring.tienda_mejorado_tienda.Service.HistorialService;
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

    //Metodos HTTP:

    @GetMapping
    public ResponseEntity<List<Historial>> getAllHistoriales() { //No se por qué me obligaba a importar de forma manual la interfaz de List, si da algun fallo provaremos con esto
        return ResponseEntity.ok(this.historialService.getAllHistoriales());
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Historial> getHistorialById(@PathVariable int id) {
        return ResponseEntity.ok(this.historialService.getHistorialById(id));
    }

    @PostMapping
    public ResponseEntity<Historial> addHistorial(@RequestBody Historial historial) {
        return ResponseEntity.ok(this.historialService.addHistorial(historial));
    }

    @PutMapping
    public ResponseEntity<Historial> updateHistorial(@RequestBody Historial historial) {
        return ResponseEntity.ok(this.historialService.updateHistorial(historial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistorial(@PathVariable int id) {
        return ResponseEntity.ok(this.historialService.deleteHistorial(id));
    }
}
