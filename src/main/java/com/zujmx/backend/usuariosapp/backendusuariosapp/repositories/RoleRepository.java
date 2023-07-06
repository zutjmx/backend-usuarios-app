package com.zujmx.backend.usuariosapp.backendusuariosapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
