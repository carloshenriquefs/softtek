package br.com.softtek.softtek.infrastructure.config.security;

import br.com.softtek.softtek.domain.user.User;
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
public class TokenService {

    @Value("minha.chave.secreta")
    private String secretWord;

    public String generateToken(User userRequestDTO){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretWord);

            String token = JWT.create()
                    .withIssuer("softtek")
                    .withSubject(userRequestDTO.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException("Não foi possível gerar o token!");
        }

    }

    public String validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretWord);

        try {
            return JWT.require(algorithm)
                    .withIssuer("softtek")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime
                .now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}