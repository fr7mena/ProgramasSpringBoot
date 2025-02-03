package org.spring.crud_basico_spring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
@CacheConfig(cacheNames = {"prestamos"})
public class ControladorPrestamo {

    //Atributos, equivalente al DAO:
    PrestamoRepository prestamoRepository;

    //Constructores:
    public ControladorPrestamo() {
    }

    @Autowired
    public ControladorPrestamo(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    //Metodos HTTP:
    @GetMapping
    public ResponseEntity<Iterable<Prestamo>> getAllPrestamos() {
        return ResponseEntity.ok(this.prestamoRepository.findAll());
    }

    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable Integer id) throws InterruptedException {
        Thread.sleep(3000);
        Prestamo prestamo = this.prestamoRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(prestamo);
    }

    @PostMapping
    public ResponseEntity<Prestamo> createPrestamo(@Valid @RequestBody Prestamo prestamo) {
        Prestamo prestamoPersistido = this.prestamoRepository.save(prestamo);
        return ResponseEntity.ok(prestamoPersistido);
    }

    @PutMapping
    public ResponseEntity<Prestamo> updatePrestamo(@Valid @RequestBody Prestamo prestamo) {
        Prestamo prestamoActualizado = this.prestamoRepository.save(prestamo);
        return ResponseEntity.ok(prestamoActualizado);
    }

   /* @DeleteMapping("/{id}")
    public ResponseEntity<Prestamo> deletePrestamo(@PathVariable Integer id) {

    }*/

}
