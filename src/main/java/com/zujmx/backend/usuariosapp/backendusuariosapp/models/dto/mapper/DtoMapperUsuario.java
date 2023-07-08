package com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.mapper;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.UsuarioDto;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

public class DtoMapperUsuario {

    private static DtoMapperUsuario mapper;

    private Usuario usuario;

    private DtoMapperUsuario() {
    }
    
    public static DtoMapperUsuario builder() {
        mapper = new DtoMapperUsuario();
        return mapper;
    }

    public DtoMapperUsuario setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return mapper;
    }

    public UsuarioDto build() {
        if (this.usuario == null){
            throw  new RuntimeException("No se ha establecido el objeto de tipo 'Usuario'");
        }

        UsuarioDto usuarioDto = new UsuarioDto(
            this.usuario.getId(), 
            this.usuario.getUsername(), 
            this.usuario.getEmail());

        return usuarioDto;
    }
    
}
