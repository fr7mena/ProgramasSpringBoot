package org.spring.trabajo_tienda_springboot.Repository;

import org.spring.trabajo_tienda_springboot.Model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository //POr cierto hay veces en las que las anotaciones se ponen en rojo generalmente es porque no se ha importado la clase asociada a esa anotacion como por ejemplo en este caso es Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    void deleteUsuarioById(Integer id);
}
