package com.orderservice.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret_key = "qwertyuiopljhgfdsazcvbnmkiopoirewsghjxzxcvbnmasdfrthjkefhsdgh";

    Key key = Keys.hmacShaKeyFor(secret_key.getBytes());

    public String getName(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Date getExpirationDate(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    private Claims getClaims(String token){

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public String createToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                .signWith(key)
                .compact();
    }

}
