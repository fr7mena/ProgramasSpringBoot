package org.spring.crud_basico_spring;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CacheConfig(cacheNames = {"usuarios"})
@RequestMapping("/usuarios")
public class ControladorUsuario {

    //Atributos de clase:
    UsuarioRepository usuarioRepository;

    //Constructores:
    public ControladorUsuario() {
    }

    @Autowired //Controlador de dependencias
    public ControladorUsuario(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Metodos propios del protocolo HTTP por parte de cliente/Navegador para tratar el metodo enviado en la solicitud y gestionar  correctamente una respuesta:
    @GetMapping
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(this.usuarioRepository.findAll()); //La funcion debe de devolver un objeto del mismo tipo que el objeto que se para por parametro en los parametros de clase del metodo
    }

    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) throws InterruptedException {
        Thread.sleep(3000);
        Usuario usuario = this.usuarioRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(usuario); //Como se puede apreciar aqui tambien se esta devolviendo el mismo tipo de objeto que el que se esta pasando en el parametro de clase
    }

    @PostMapping
    public ResponseEntity<Usuario> addUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioPersistido = this.usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioPersistido);
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = this.usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id) {
        this.usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario con id: " + id + " eliminado"); //Como se puede apreciar aquí siempre se devuelve el mismo tipo de objeto que el del parametro de clase.
    }
}
