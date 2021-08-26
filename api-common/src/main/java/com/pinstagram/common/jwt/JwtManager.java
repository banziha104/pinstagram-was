package com.pinstagram.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtManager {
    private static final long EXPIRED_TIME = 1000 * 60L * 60L * 24L * 30L;
    private static final byte[] SECRET_KEY = "pinstagramsecret".getBytes(StandardCharsets.UTF_8);


    public String createToken(String email, String name, long id){
        Map<String, Object> heaaders = new HashMap<>();
        heaaders.put("type","JWT");
        heaaders.put("alg","HS256");

        Map<String,Object> payloads = new HashMap<>();
        payloads.put("email",email);
        payloads.put("name",name);
        payloads.put("id",id);

        Date ext = new Date();
        ext.setTime(ext.getTime() + EXPIRED_TIME);

        return Jwts
                .builder()
                .setHeader(heaaders)
                .setClaims(payloads)
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }
}
