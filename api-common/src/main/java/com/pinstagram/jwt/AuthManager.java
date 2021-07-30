package com.pinstagram.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class AuthManager {
    public boolean isAdmin(Authentication authentication){
        System.out.println("1");
        if (authentication == null){
            System.out.println("2");
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims){
            System.out.println("3");
            return ((Claims) principal).get("email").equals("admin@admin.com");
        }

        System.out.println("4");
        return false;
    }

    public boolean isValidateToken(Authentication authentication){
        if (authentication == null){
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims){
            Claims claims = (Claims) principal;
            Date expiration = claims.get("exp", Date.class);
            return new Date().getTime() >= expiration.getTime();
        }

        return false;
    }
}
