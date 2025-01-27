package org.example.springprimerprograma;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coches")
public class ControladorCoche {

    @GetMapping("/coche")
    public ResponseEntity<Coche> obtenerCoche() {
        Coche coche = new Coche("Ford", "Deportivo", "Color", "Diésel", 2000, 1200);
        return ResponseEntity.ok(coche);
    }

    @PostMapping("/coche")
    public ResponseEntity<Coche> ejemploPostCoche(@RequestBody Coche c) {
        System.out.println("Por consola -> \n" + c);
        return ResponseEntity.ok(c);

    }
}
