package com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequest implements IUsuario {

    @NotBlank(message = "es requerido")
    @Size(min = 5, max = 100)
    @Column(unique = true)
    private String username;

    @NotBlank(message = "es requerido")
    @Email
    @Column(unique = true)
    private String email;

    private boolean admin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isAdmin() {
        return admin;
    }
    
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
