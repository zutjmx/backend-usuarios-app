package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.List;
import java.util.Optional;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.UsuarioDto;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.UsuarioRequest;

public interface UsuarioService {
    List<UsuarioDto> findAll();
    Optional<UsuarioDto> findById(Long id);
    UsuarioDto save(Usuario usuario);
    Optional<UsuarioDto> update(UsuarioRequest usuario, Long id);
    void remove(Long id);
}
