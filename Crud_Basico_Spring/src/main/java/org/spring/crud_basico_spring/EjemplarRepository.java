package org.spring.crud_basico_spring;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends CrudRepository<Ejemplar, Integer> {

}
