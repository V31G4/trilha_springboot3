package com.trilha.exercicio3.controllers;

import com.trilha.exercicio3.usuarios.Usuario;
import com.trilha.exercicio3.usuarios.UsuarioRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public record CreateUserDTO(@NotNull String login, @NotNull String senha){}

    @PostMapping(value = "/user/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO){
        Usuario newUsuario = new Usuario(createUserDTO.login(), passwordEncoder.encode(createUserDTO.senha()) );

        usuarioRepository.save(newUsuario);

        return ResponseEntity.ok().body("Salvo.");
    }
}
