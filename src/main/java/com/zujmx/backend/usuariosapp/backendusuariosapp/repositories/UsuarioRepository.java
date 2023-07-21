package com.zujmx.backend.usuariosapp.backendusuariosapp.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    @Query("select u from Usuario u where u.username = ?1")
    Optional<Usuario> getUserByUsername(String username);

    Page<Usuario> findAll(Pageable pageable);
}
