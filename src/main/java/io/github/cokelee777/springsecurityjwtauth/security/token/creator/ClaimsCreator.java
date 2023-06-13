package io.github.cokelee777.springsecurityjwtauth.security.token.creator;

import io.github.cokelee777.springsecurityjwtauth.security.auth.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ClaimsCreator {

    public static <T extends JwtUserDetails> Claims setClaims(T jwtUserDetails) {
        Claims claims = Jwts.claims();
        claims.put("identifier", jwtUserDetails.getUsername());
        claims.put("nickname", jwtUserDetails.getNickname());
        claims.put("role", jwtUserDetails.getRole().toString());
        claims.setSubject(jwtUserDetails.getId().toString());
        return claims;
    }
}
