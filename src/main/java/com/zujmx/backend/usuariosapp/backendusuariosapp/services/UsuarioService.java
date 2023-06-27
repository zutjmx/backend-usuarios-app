package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.List;
import java.util.Optional;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    Optional<Usuario> update(Usuario usuario, Long id);
    void remove(Long id);
}
