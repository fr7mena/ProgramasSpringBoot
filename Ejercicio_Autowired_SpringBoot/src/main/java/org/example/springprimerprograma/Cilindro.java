package org.example.springprimerprograma;

public class Cilindro {
    private String marca;
    private String calidad;

    public Cilindro() {
    }

    public Cilindro(String marca, String calidad) {
        this.marca = marca;
        this.calidad = calidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCalidad() {
        return calidad;
    }
    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }
}
