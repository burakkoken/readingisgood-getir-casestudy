package org.readingisgood.ordermicroservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.readingisgood.ordermicroservice.config.JwtClaims;
import org.readingisgood.ordermicroservice.config.JwtConfig;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private JwtConfig jwtConfig;

    public JwtTokenUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Long getUserIdFromToken(String token) {
        Integer userId = getAllClaimsFromToken(token).get(JwtClaims.USER_ID, Integer.class);
        if(userId != null) {
            return userId.longValue();
        }
        return null;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        String pureToken = token.replace(jwtConfig.getPrefix(), "");
        return Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(pureToken).getBody();
    }

}