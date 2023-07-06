package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Role;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.UsuarioRequest;
import com.zujmx.backend.usuariosapp.backendusuariosapp.repositories.RoleRepository;
import com.zujmx.backend.usuariosapp.backendusuariosapp.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        String passwordCifrado = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrado);

        Optional<Role> rOptional = roleRepository.findByName("ROLE_USER");        
        List<Role> roles = new ArrayList<>();
        if(rOptional.isPresent()) {
            roles.add(rOptional.orElseThrow());
        }
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(UsuarioRequest usuario, Long id) {
        Optional<Usuario> uOptional = this.findById(id);
        if(uOptional.isPresent()) {
            Usuario usuarioBD = uOptional.orElseThrow();
            usuarioBD.setUsername(usuario.getUsername());
            usuarioBD.setEmail(usuario.getEmail());
            return Optional.of(this.save(usuarioBD));
        }
        return Optional.empty();
    }

}
