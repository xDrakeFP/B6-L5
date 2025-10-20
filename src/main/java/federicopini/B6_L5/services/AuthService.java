package federicopini.B6_L5.services;

import federicopini.B6_L5.entities.Dipendente;
import federicopini.B6_L5.exceptions.UnauthorizedException;
import federicopini.B6_L5.payloads.AuthRequest;
import federicopini.B6_L5.security.JWTTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialsAndGenerateToken(AuthRequest body){
        Dipendente found = this.dipendenteService.findByEmail(body.email());

        if(found.getPassword().equals(body.password())) {
            return jwtTools.createToken(found);
        } else {throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
