package com.example.movie.services;

import com.example.movie.models.Token;
import com.example.movie.models.entities.RoleDAO;
import com.example.movie.models.entities.UserDAO;
import com.example.movie.models.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final static String SECRET_KEY = "20e08ccd49b555e005d94eddb625d13293cd5dd76ccce7945fa3c206eed3628e";
    private final static long USAGE = 24 * 60 * 60 * 1000; // 1 day

    public Token generateToken(UserDetailsImpl user){
        Token token = new Token();
        token.setSubject(user.getUsername());
        token.setUsage(USAGE);
        token.setIsSuedAt(new Date(System.currentTimeMillis()));
        token.setExpiredAt(new Date(System.currentTimeMillis() + USAGE));
        token.setValue(Jwts
                .builder()
                .subject(token.getSubject())
                .issuedAt(token.getIsSuedAt())
                .expiration(token.getExpiredAt())
                .signWith(getSigninKey())
                .compact());
        
        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpired(token).isBefore(LocalDate.now());
    }

    public LocalDate extractExpired(String token) {
        Date expired = extractClaims(token, Claims::getExpiration);
        LocalDate expLocalDate = LocalDate.of(
                expired.getYear()+1900, expired.getMonth()+1, expired.getDate());
        System.out.println("ExpLocaldate: " + expLocalDate);
        return expLocalDate;
    }
}
