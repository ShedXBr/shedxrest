package com.shedx.shedxrest.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    
    public String generateToken(Long userId, Long CompanyId){
        Map<String, Object> claims = new HashMap<>();
        claims.put("CompanyId", CompanyId);
        return Jwts.builder()
        .setClaims(claims)
        .setSubject(userId.toString())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+100*60*60))
        .signWith(SECRET)
        .compact();
    }
    public Claims extractAllCLaims(String token){
        return Jwts.parserBuilder()
        .setSigningKey(SECRET)
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    public Long extractUserId(String token){
        return Long.parseLong(extractAllCLaims(token).getSubject());
    }
    public Long extractCompanyId(String token){
        return extractAllCLaims(token).get("CompanyId", Long.class);
    }
}
//receive the token and validate
