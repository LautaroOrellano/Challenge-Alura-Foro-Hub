package com.ForoHub.ForoHub.infra.seguridad;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ForoHub.ForoHub.domain.autores.Autor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarJWT(Autor autor) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            String token = JWT.create()
                    .withIssuer("ForHub")
                    .withSubject(autor.getUsername())
                    .withClaim("id", autor.getId())
                    .withExpiresAt(generarFechaDeExpiracion())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Configuración de firma no válida / No se pudieron convertir reclamos");
        }
    }

    public Instant generarFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("El token no debe ser null");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("ForHub")
                    .build()
                    .verify(token);
            String subject = verifier.getSubject();
            if (subject == null) {
                throw new RuntimeException("Verifier invalido");
            }
            return subject;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("token invalido", exception);
        }

    }
}
