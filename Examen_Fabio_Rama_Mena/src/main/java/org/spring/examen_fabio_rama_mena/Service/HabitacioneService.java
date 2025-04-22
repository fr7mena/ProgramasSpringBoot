package org.spring.examen_fabio_rama_mena.Service;

import org.spring.examen_fabio_rama_mena.Model.Habitacione;
import org.spring.examen_fabio_rama_mena.Repository.HabitacioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HabitacioneService {

    private HabitacioneRepository habitacioneRepository;

    @Autowired
    public HabitacioneService(HabitacioneRepository habitacioneRepository) {
        this.habitacioneRepository = habitacioneRepository;
    }

    //Metdos:
    public List<Habitacione> getAllHabitaciones() {
        return habitacioneRepository.findAll();
    }

    public Habitacione getHabitacioneById(String id) {
        try {
            return this.habitacioneRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("La habitación no ha sido encontrada");
        }
    }

    public Habitacione addHabitacione(Habitacione habitacione) {
        return this.habitacioneRepository.save(habitacione);
    }
}
