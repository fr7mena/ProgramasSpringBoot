package org.spring.trabajo_tienda_springboot.Service;

import org.spring.trabajo_tienda_springboot.Model.Usuario;
import org.spring.trabajo_tienda_springboot.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    //Propiedades de la clase
    @Autowired
    private UsuarioRepository usuarioRepository;

    //Gestion CRUD:
    //GetAll
    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    //GetById
    public Optional<Usuario> getUsuarioById(int id) { //Es recomendable usar El objeto de tipo Optional porque te meneja mejor o te quita de problemas con las excepciones de tipo NUllException
        return usuarioRepository.findById(id);
    }

    //InsertInto:
    public Usuario insertUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    //Update:
    public Usuario updateUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = this.getUsuarioById(usuario.getId());
        if (usuarioOptional.isPresent()) {
            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }
    //DeteleById: A VER Y QUE POR FAVOR DIGA QUÉ METODOS USAR EN EL CRUD
    public void deleteUsuario(int id) {
        usuarioRepository.deleteById(id);
    }
}
