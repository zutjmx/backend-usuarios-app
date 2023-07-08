package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.UsuarioDto;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.dto.mapper.DtoMapperUsuario;
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
    public List<UsuarioDto> findAll() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

        return usuarios
                .stream()
                .map(u -> DtoMapperUsuario.builder().setUsuario(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDto> findById(Long id) {
        return usuarioRepository
                .findById(id)
                .map(u -> DtoMapperUsuario.builder().setUsuario(u).build());
        
    }

    @Override
    @Transactional
    public UsuarioDto save(Usuario usuario) {
        String passwordCifrado = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCifrado);

        Optional<Role> rOptional = roleRepository.findByName("ROLE_USER");        
        List<Role> roles = new ArrayList<>();
        if(rOptional.isPresent()) {
            roles.add(rOptional.orElseThrow());
        }
        usuario.setRoles(roles);

        return DtoMapperUsuario.builder()
                .setUsuario(usuarioRepository.save(usuario))
                .build();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> update(UsuarioRequest usuario, Long id) {
        Optional<Usuario> o = usuarioRepository.findById(id);
        Usuario usuarioOptional = null;
        
        if(o.isPresent()) {
            Usuario usuarioBD = o.orElseThrow();
            usuarioBD.setUsername(usuario.getUsername());
            usuarioBD.setEmail(usuario.getEmail());
            usuarioOptional = usuarioRepository.save(usuarioBD);
        }

        return Optional.ofNullable(DtoMapperUsuario.builder()
                .setUsuario(usuarioOptional)
                .build());
    }

}
