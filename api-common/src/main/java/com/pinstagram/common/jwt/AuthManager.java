package com.pinstagram.common.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Optional;

public class AuthManager {
    public boolean isAdmin(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims) {
            return ((Claims) principal).get("email").equals("admin@admin.com");
        }
        return false;
    }

    public boolean isValidateToken(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();

        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            Date expiration = claims.get("exp", Date.class);
            return new Date().getTime() <= expiration.getTime();
        }

        return false;
    }

    public boolean isSameId(Authentication authentication, long id) {
        if (authentication == null) {
            return false;
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            var authId = claims.get("id", Long.class);
            return authId == id;
        }else{
            return false;
        }
    }

    public Optional<Long> getId(Authentication authentication){
        if (authentication == null) {
            return Optional.empty();
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof Claims) {
            Claims claims = (Claims) principal;
            var id = claims.get("id", Long.class);
            return Optional.ofNullable(id);
        }else{
            return Optional.empty();
        }
    }
}
