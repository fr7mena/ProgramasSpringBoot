package org.example.springprimerprograma;

public class Bujia {
    private String marca;
    private String calidad;

    public Bujia() {
    }

    public Bujia(String marca, String calidad) {
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

    @Override
    public String toString() {
        return "Bujia{" +
                "marca='" + marca + '\'' +
                ", calidad='" + calidad + '\'' +
                '}';
    }
}
