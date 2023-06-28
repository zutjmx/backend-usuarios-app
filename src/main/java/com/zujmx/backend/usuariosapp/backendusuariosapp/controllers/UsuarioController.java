package com.zujmx.backend.usuariosapp.backendusuariosapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.zujmx.backend.usuariosapp.backendusuariosapp.models.entities.Usuario;
import com.zujmx.backend.usuariosapp.backendusuariosapp.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(originPatterns = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, 
        BindingResult result
    ) {
        if(result.hasErrors()) {
            return validacion(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@Valid @PathVariable Long id,         
        @RequestBody Usuario usuario,
        BindingResult result
    ) {
        if(result.hasErrors()) {
            return validacion(result);
        }
        Optional<Usuario> uOptional = usuarioService.update(usuario,id);
        if(uOptional.isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(uOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrar(@PathVariable Long id) {
        Optional<Usuario> uOptional = usuarioService.findById(id);
        if(uOptional.isPresent()) {
            usuarioService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Método para generar e insertar datos en la tabla usuarios.
    @PostMapping("/genera/{cuantos}")
    public String generaUsuarios(@PathVariable(name = "cuantos") int cuantos) {
        Faker faker = new Faker(new Locale("es-MX"));
        for (int i = 0; i < cuantos; i++) {
            Usuario usuario = new Usuario();
            usuario.setUsername(faker.name().username());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword(faker.internet().password());
            usuarioService.save(usuario);
        }
        return "Ok";
    }

    private ResponseEntity<?> validacion(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), 
                        "El campo ".concat(err.getField())
                        .concat(" ")
                        .concat(err.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
