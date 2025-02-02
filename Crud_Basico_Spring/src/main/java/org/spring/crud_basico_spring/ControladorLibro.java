package org.spring.crud_basico_spring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
@CacheConfig(cacheNames = {"libros"})
public class ControladorLibro {
    //Atributos de la clase:
    LibroRepository libroRepository; //Este atributo es completamente necesario para poder usar los metodos necesarios para poder gestionar la base de datos desde Spring

    //Contructores:

    public ControladorLibro() {
    }

    @Autowired
    public ControladorLibro(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    } //Me guestaría saber que hace exactamente este contructor y por qué es indispensable.

    //METODOS HTTP:
    @GetMapping
    public ResponseEntity<Iterable<Libro>> getAllLibros() {
        return ResponseEntity.ok(this.libroRepository.findAll());
    }

    @Cacheable
    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> getLibroById(@PathVariable String isbn) throws InterruptedException { //CUANDO RECUPERAMOS O NECESITAMOS TRATAR UN OBJETO O REGISTRO DE LA BASE DE DATOS Y NECESITAMOS HACERLO A CON EL USO DE UNA VARIABLE REPRESENTATIVA DE UNO DE LOS CAMPOS DE LA ENTIDAD Y, A SU VEZ, UN ATRIBUTO DEL OBEJETO, HAY QUE USAR LA ANOTACION @PathVariable PARA INDICAR QUE LA VARIABLE VA A VIAJAR EN LA PROPIA URL Y QUE LA SOLICITUD SE TRATE COMO TAL.
        Thread.sleep(3000);
        Libro libro = this.libroRepository.findById(isbn).orElseThrow();
        return ResponseEntity.ok(libro);
    }

    @PostMapping
    public ResponseEntity<Libro> addLibro(@Valid @RequestBody Libro libro) {
        Libro libroPersistido  = this.libroRepository.save(libro);
        return ResponseEntity.ok(libroPersistido);
    }

    @PutMapping
    public ResponseEntity<Libro> updateLibro(@Valid @RequestBody Libro libro) { //CUANDO NECESITAMOS TRATAR UN OBJETO O REGISTRO DE LA BASE DE DATOS Y NECESITAMOS HACERLO DE TAL FORMA QUE LA INFORMACION, COMO ES SIEMPRE EN EL CASO DE LOS FORMULARIO, VIAJE EN EL CUERPO DE LA SOLICITUD NECESITAMOS PONER EN EL OBJETO QUE PASAMOS COMO PARAMETRO LA SIGUIENTE ANOTACION: @RequestBody; PARA QUE JUSTAMENTE LA INFORMACION VIAJE EN EL CUERPO/BODY DE LA SOLICITUD/REQUEST. DE FORMA ADICCIONAL PODEMOS PASAR LA ANOTACION: @Valid, PARA RESPETAR AQUELLAS VALIDACIONES QUE PODEMOS ABSTRAER DIRECTAMENTE DE LA BASE DE DATOS.
        Libro libroPersistido  = this.libroRepository.save(libro);
        return ResponseEntity.ok(libroPersistido);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable String isbn) {
        this.libroRepository.deleteById(isbn);
        return ResponseEntity.ok("Libro con isbn: " + isbn + " eliminado");
    }
}
