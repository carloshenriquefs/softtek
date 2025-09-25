package br.com.softtek.softtek.infrastructure.config.security;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static br.com.softtek.softtek.infrastructure.constants.Messages.NOT_POSSIBLE_TO_GENERATE_TOKEN;

@Service
public class TokenService {

    @Value("${my.secret.key}")
    private String secretWord;

    public String generateToken(MongoUserEntity mongoUserEntity){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretWord);

            String token = JWT
                    .create()
                    .withIssuer("softtek")
                    .withSubject(mongoUserEntity.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e){
            throw new RuntimeException(NOT_POSSIBLE_TO_GENERATE_TOKEN);
        }

    }

    public String validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretWord);

        try {
            return JWT
                    .require(algorithm)
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
