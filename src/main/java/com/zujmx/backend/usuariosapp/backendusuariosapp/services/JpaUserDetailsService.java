package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;
import com.zujmx.backend.usuariosapp.backendusuariosapp.repositories.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //Optional<Usuario> uOptional = usuarioRepository.findByUsername(username);
        Optional<Usuario> uOptional = usuarioRepository.getUserByUsername(username);

        if(!uOptional.isPresent()) {            
            throw new UsernameNotFoundException("El usuario: ".concat(username).concat(" no existe en el sistema")); 
        }

        Usuario usuario = uOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles()
                                                .stream()
                                                .map(r -> new SimpleGrantedAuthority(r.getName()))
                                                .collect(Collectors.toList());

        // $2a$12$6ODzlbsDTjIqXLFRtGwxEOt3PWYsCTkSlyXAhm1h.bdy3oMiAxoo6 = sistemas
        return new User(usuario.getUsername(),
            usuario.getPassword(),
            true,
            true,
            true,
            true,
            authorities);
    }
    
}
