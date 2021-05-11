/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author gamma
 */
public class JWTHandling {

    private static Key key;

    public static String createJWT(String username) {
        String jws = Jwts.builder().setSubject(username).signWith(getKey()).compact();
        try {
            assert Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jws)
                    .getBody()
                    .getSubject()
                    .equals(username);
            return jws;
        } catch (JwtException e) {
            //TODO LOG
            e.printStackTrace();
            return null;
        }
    }

    public static String readJWT(String jwt) {
        Jws<Claims> jws;

        try {
            jws = Jwts.parserBuilder() // (1)
                    .setSigningKey(getKey()) // (2)
                    .build() // (3)
                    .parseClaimsJws(jwt); // (4)

            // we can safely trust the JWT
            return jws.getBody().get("sub", String.class);
        } catch (JwtException ex) {       // (5)
            
            // we *cannot* use the JWT as intended by its creator
            //TODO LOG
            ex.printStackTrace();
            return null;
        }
    }

    public static Key getKey() {
        if (key == null) {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return key;

    }
}
