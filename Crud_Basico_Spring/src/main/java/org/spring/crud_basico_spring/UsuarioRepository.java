package org.spring.crud_basico_spring;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> { //Entre los parametros de clase se debe colocar la clase equivalente a la entidad de la base de datos y también el tipo de dato equivalente a la primary key de la propia entidad

}

