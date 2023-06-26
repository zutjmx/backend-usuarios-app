package com.zujmx.backend.usuariosapp.backendusuariosapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    
}
