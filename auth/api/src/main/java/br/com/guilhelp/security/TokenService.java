package br.com.guilhelp.security;


import br.com.guilhelp.entities.User;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class TokenService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time-in-seconds}")
    private int expirationTimeInSeconds;

    public String generateToken(User user) {
        var issuedInstant = Instant.now();
        var expirationInstant = issuedInstant.plusSeconds(expirationTimeInSeconds);
        return Jwts.builder()
            .issuer("auth")
            .subject(user.id())
            .issuedAt(Date.from(issuedInstant))
            .expiration(Date.from(expirationInstant))
            .signWith(
                    Keys.hmacShaKeyFor(
                            secretKey.getBytes()
                    ),
                    Jwts.SIG.HS256
            )
            .compact();
    }
}
