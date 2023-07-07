package com.zujmx.backend.usuariosapp.backendusuariosapp.auth.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static com.zujmx.backend.usuariosapp.backendusuariosapp.auth.TokenJwtConfig.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {        
        Usuario usuario = null;
        String username = null;
        String password = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = usuario.getUsername();
            password = usuario.getPassword();
            //logger.info("username desde request: "+username);
            //logger.info("password desde request: "+password);
        } catch (StreamReadException e) {
            
            e.printStackTrace();
        } catch (DatabindException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        boolean isAdmin = roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        claims.put("isAdmin", isAdmin);
        claims.put("username", username);
        
        String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .signWith(SECRET_KEY)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .compact();

        logger.info("token generado: "+token);
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("mensaje", "Se inició sesión con éxito, usuario: " + username);
        body.put("username", username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Ocurrió un error en la Autenticación, usuario o contraseña incorrectos");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
    
}
