package com.msp.security;



import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "MySuperSecretKeyForJWTAuthenticationInMSP2026";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {

        try {

            Claims claims = extractAllClaims(token);

            return !claims.getExpiration()
                    .before(new Date());

        } catch (ExpiredJwtException e) {

            return false;

        } catch (Exception e) {

            return false;
        }
    }

    public String extractEmail(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    public Long extractUserId(String token) {

        Integer id = extractAllClaims(token)
                .get("userId", Integer.class);

        return id.longValue();
    }
}
