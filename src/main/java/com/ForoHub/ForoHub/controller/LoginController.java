package com.ForoHub.ForoHub.controller;

import com.ForoHub.ForoHub.domain.autores.Autor;
import com.ForoHub.ForoHub.domain.autores.AutenticacionAutor;
import com.ForoHub.ForoHub.infra.seguridad.DatosJWTToken;
import com.ForoHub.ForoHub.infra.seguridad.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacionAutor datosAutenticacionAutor) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionAutor.login(), datosAutenticacionAutor.clave());
            var authentication = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarJWT((Autor) authentication.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(403).body("Credenciales incorrectas");
        }
    }
    public static class AuthResponse {
        private String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

