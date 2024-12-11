package com.nutech.hometest.config;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {

    @Value("${security.secret-key:null}")
    private String secretKey;

    public String generateToken(String email) {
        log.debug("Secret Key : {}", secretKey);
        long expirationTime = 900000;//43200000;
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);
        
        return Jwts.builder()
                .claim("email",email)
                .expiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (expirationDate.before(now)) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token sudah kadaluarsa.");
        } catch (SignatureException e) {
            System.out.println("Token tidak valid (Signature tidak cocok).");
        } catch (JwtException e) {
            System.out.println("Token tidak valid.");
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> decodeJWT(String token) {
        String[] parts = token.split("\\.");
        
        if (parts.length != 3) {
            throw new IllegalArgumentException("Token JWT tidak valid.");
        }

        try {
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> header = objectMapper.readValue(headerJson, Map.class);
            Map<String, Object> payload = objectMapper.readValue(payloadJson, Map.class);

            System.out.println("Header: " + header);
            System.out.println("Payload: " + payload);

            return payload;
        } catch (Exception e) {
            throw new RuntimeException("Error decoding JWT", e);
        }
    }
}
