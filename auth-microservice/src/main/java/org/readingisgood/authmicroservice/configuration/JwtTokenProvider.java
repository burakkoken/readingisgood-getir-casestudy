package org.readingisgood.authmicroservice.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.readingisgood.authmicroservice.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private JwtConfig jwtConfig;

    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.USER_ID, customer.getId());
        return  Jwts.builder().setClaims(claims)
                .setIssuer(JwtClaims.ISSUER)
                .setSubject(customer.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

}
