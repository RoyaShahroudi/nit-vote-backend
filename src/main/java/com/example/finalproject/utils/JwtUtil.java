package com.example.finalproject.utils;

import com.example.finalproject.dto.StudentDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRE_DURATION = 1000L * 3600 * 24 * 30;

    public String generateAccessToken(StudentDTO studentDTO) {
        return Jwts.builder()
                .setSubject(String.valueOf(studentDTO.getStudentNumber()))
                .setIssuer("CodeJava")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, "SECRET_KEY")
                .compact();
    }

    public Boolean validateJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token).getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return !isTokenExpired;
    }

    public String getStudentNumber(String token) {
        final Claims claims = Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
