import io.jsonwebtoken.security.Keys;

import java.util.Base64;

public class ciao {
    public static void main(String[] args) {
        var key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
