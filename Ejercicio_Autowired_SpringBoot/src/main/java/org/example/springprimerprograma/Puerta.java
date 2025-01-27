package org.example.springprimerprograma;

import org.springframework.beans.factory.annotation.Autowired;

public class Puerta {
    private  String material;
    private  String color;

    public Puerta() {
    }

    @Autowired
    public Puerta(String material, String color) {
        this.material = material;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Puerta{" +
                "material='" + material + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
