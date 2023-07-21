package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.UsuarioDto;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.UsuarioRequest;

public interface UsuarioService {
    List<UsuarioDto> findAll();
    Page<UsuarioDto> findAll(Pageable pageable);
    Optional<UsuarioDto> findById(Long id);
    UsuarioDto save(Usuario usuario);
    Optional<UsuarioDto> update(UsuarioRequest usuario, Long id);
    void remove(Long id);
}
