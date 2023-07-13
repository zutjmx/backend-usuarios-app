package com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.mapper;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.UsuarioDto;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

public class DtoMapperUsuario {

    private Usuario usuario;

    private DtoMapperUsuario() {
    }
    
    public static DtoMapperUsuario builder() {
        return new DtoMapperUsuario();
    }

    public DtoMapperUsuario setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public UsuarioDto build() {
        if (this.usuario == null){
            throw  new RuntimeException("No se ha establecido el objeto de tipo 'Usuario'");
        }

        boolean isAdmin = usuario.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));

        UsuarioDto usuarioDto = new UsuarioDto(
            this.usuario.getId(), 
            this.usuario.getUsername(), 
            this.usuario.getEmail(), 
            isAdmin);

        return usuarioDto;
    }
    
}
