package federicopini.B6_L5.security;


import federicopini.B6_L5.exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import federicopini.B6_L5.entities.Dipendente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTools {
private Key key;

public JWTTools(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
}

public String createToken(Dipendente dipendente) {
    return Jwts.builder().issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .subject(String.valueOf(dipendente.getId()))
            .signWith(key)
            .compact();
}

public void verifyToken(String token) {
    try {
        Jwts.parser().verifyWith((SecretKey) this.key).build().parse(token);
    } catch (Exception ex) {
        throw new UnauthorizedException("Ci sono stati errori nel token! Effettua di nuovo il login!");
    }

}

}

