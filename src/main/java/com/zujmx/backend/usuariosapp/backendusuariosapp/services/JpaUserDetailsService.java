package com.zujmx.backend.usuariosapp.backendusuariosapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("zutjmx")) {            
            throw new UsernameNotFoundException("El usuario: ".concat(username).concat(" no existe en el sistema")); 
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // $2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS = 12345
        // $2a$12$6ODzlbsDTjIqXLFRtGwxEOt3PWYsCTkSlyXAhm1h.bdy3oMiAxoo6 = sistemas
        return new User(username,
              "$2a$12$6ODzlbsDTjIqXLFRtGwxEOt3PWYsCTkSlyXAhm1h.bdy3oMiAxoo6",
              true,
              true,
              true,
              true,
              authorities);
    }
    
}
