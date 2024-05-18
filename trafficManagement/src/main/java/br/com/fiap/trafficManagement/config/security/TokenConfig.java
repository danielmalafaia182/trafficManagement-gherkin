package br.com.fiap.trafficManagement.config.security;

import br.com.fiap.trafficManagement.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenConfig {

    @Value("${passPhrase}")
    private String passPhrase;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passPhrase);
            String token = JWT
                    .create()
                    .withIssuer("trafficManagement")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationTime())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error generating token!");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(passPhrase);
            return JWT
                    .require(algorithm)
                    .withIssuer("trafficManagement")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException error) {
            throw new RuntimeException("Token not valid or expired!");
        }
    }

    public Instant generateExpirationTime() {
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
    }
}
