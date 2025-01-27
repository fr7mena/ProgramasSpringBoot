package org.example.springprimerprograma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Coche {
   private Motor motor;
   private Puerta puerta;

    public Coche() {
    }

    @Autowired
    public Coche(Motor motor, Puerta puerta) {
        this.motor = motor;
        this.puerta = puerta;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Puerta getPuerta() {
        return puerta;
    }

    public void setPuerta(Puerta puerta) {
        this.puerta = puerta;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "motor=" + motor +
                ", puerta=" + puerta +
                '}';
    }
}
