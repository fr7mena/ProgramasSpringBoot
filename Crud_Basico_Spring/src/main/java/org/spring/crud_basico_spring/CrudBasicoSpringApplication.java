package org.spring.crud_basico_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrudBasicoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudBasicoSpringApplication.class, args);
    }

}
