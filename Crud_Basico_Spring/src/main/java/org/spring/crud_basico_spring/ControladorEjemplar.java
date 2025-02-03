package org.spring.crud_basico_spring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ejemplares")
@CacheConfig(cacheNames = {"ejemplares"}) //Recuerda que se pone entre llaves dandole valor a la propiedad cachesNames
public class ControladorEjemplar {
    EjemplarRepository ejemplarRepository; //Este atributo ya lo he comentado en otras clases pero básicamente es el DAOEjemplar que teníamos en PHP o en JDBC, es decir, nos habilita los metodos necesario para manejar y que las modificaciones que hacemos a través del código persistan.
    LibroRepository libroRepository;
    //Constructores:

    public ControladorEjemplar() {
    }

    @Autowired
    public ControladorEjemplar(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    //Metodos que pasamos en la URL HTTP:

    @GetMapping
    public ResponseEntity<Iterable<Ejemplar>> getAllEjemplars() {
        return ResponseEntity.ok(this.ejemplarRepository.findAll());
    }

    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> getEjemplar(@PathVariable Integer id) throws InterruptedException {
        Thread.sleep(3000);
        Ejemplar ejemplar = this.ejemplarRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(ejemplar);
    }

    @PostMapping
    public ResponseEntity<Ejemplar> addEjemplar(/*@Valid*/ @RequestBody Ejemplar ejemplar) {
        /*this.libroRepository.findById(ejemplar.getIsbn().getIsbn());*/
        Ejemplar ejemplarPersistido = this.ejemplarRepository.save(ejemplar);
        return ResponseEntity.ok(ejemplarPersistido);
    }

    @PutMapping
    public ResponseEntity<Ejemplar> updateEjemplar(@Valid @RequestBody Ejemplar ejemplar) {
        Ejemplar ejemplarActualizado = this.ejemplarRepository.save(ejemplar); //Como se puede apreciar tanto en el metodo POST como PUT se utiliza el metedo save de la clase EjemplarRepository que simplemente es una clase que principalmente implementa los metodos de la clase/Interfaz CrudRepository<Ejemplar, Integer>, si el registro no estaba almacenado en la base de datos el elementos se inserta pero si ya estuviera como es la intencion de este caso el elemento se actualiza
        return ResponseEntity.ok(ejemplarActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEjemplar(@PathVariable Integer id) {
        this.ejemplarRepository.deleteById(id);
        return ResponseEntity.ok("El ejemplar con id " + id + "ha sido eliminado correctamente." );
    }
}
