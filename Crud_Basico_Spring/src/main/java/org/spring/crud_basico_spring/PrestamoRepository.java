package org.spring.crud_basico_spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends CrudRepository<Prestamo, Integer> {

}
