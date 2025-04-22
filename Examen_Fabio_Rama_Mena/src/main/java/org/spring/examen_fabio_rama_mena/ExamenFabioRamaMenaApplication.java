package org.spring.examen_fabio_rama_mena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExamenFabioRamaMenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamenFabioRamaMenaApplication.class, args);
    }

}
