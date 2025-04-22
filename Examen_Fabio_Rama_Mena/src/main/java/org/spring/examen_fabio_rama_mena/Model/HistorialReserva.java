package org.spring.examen_fabio_rama_mena.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "historial_reservas")
public class HistorialReserva {
    @Id
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "motivo", length = 200)
    private String motivo;

    public HistorialReserva(Integer id, String motivo) {
        this.id = id;
        this.motivo = motivo;
    }

    public HistorialReserva() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}