package com.app.quantitymeasurement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }

    public String generateToken(
            String email,
            String name,
            String picture
    ) {

        Date now = new Date();

        Date expiryDate =
                new Date(
                        now.getTime()
                                + jwtExpiration
                );

        return Jwts.builder()

                .setSubject(email)

                .claim("name", name)

                .claim("picture", picture)

                .setIssuedAt(now)

                .setExpiration(expiryDate)

                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    public String extractEmail(
            String token
    ) {

        return getClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token
    ) {

        try {

            getClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Claims getClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(
                        getSigningKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();
    }
}