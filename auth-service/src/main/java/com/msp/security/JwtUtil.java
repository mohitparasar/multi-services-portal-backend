package com.msp.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Must be at least 32 characters
    private static final String SECRET_KEY =
            "MySuperSecretKeyForJWTAuthenticationInMSP2026";

    private final Key key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8));



    // ==========================
    // Generate Access Token
    // ==========================

    public String generateToken(

            Long userId,

            String email,

            String role) {


        return Jwts.builder()

                .subject(email)

                .claim("userId", userId)

                .claim("role", role)

                .issuedAt(new Date())

                .expiration(

                        new Date(

                                System.currentTimeMillis()

                                        + 1000 * 60 * 15
                        )
                )

                .signWith(key)

                .compact();
    }



    // ==========================
    // Generate Refresh Token
    // ==========================

    public String generateRefreshToken() {

        return UUID.randomUUID().toString();
    }



    // ==========================
    // Extract All Claims
    // ==========================

    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith((javax.crypto.SecretKey) key)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }



    // ==========================
    // Extract Email
    // ==========================

    public String extractEmail(String token) {

        return extractAllClaims(token)

                .getSubject();
    }



    // ==========================
    // Extract Role
    // ==========================

    public String extractRole(String token) {

        return extractAllClaims(token)

                .get("role", String.class);
    }



    // ==========================
    // Extract UserId
    // ==========================

    public Long extractUserId(String token) {

        Integer id =

                extractAllClaims(token)

                        .get("userId", Integer.class);


        return id.longValue();
    }



    // ==========================
    // Validate Token
    // ==========================

    public boolean validateToken(String token) {

        try {

            Claims claims =

                    extractAllClaims(token);


            return !claims

                    .getExpiration()

                    .before(new Date());

        }

        catch (ExpiredJwtException e) {

            return false;
        }

        catch (Exception e) {

            return false;
        }
    }

}