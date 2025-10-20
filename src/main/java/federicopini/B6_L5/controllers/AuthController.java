package federicopini.B6_L5.controllers;


import federicopini.B6_L5.entities.Dipendente;
import federicopini.B6_L5.exceptions.ValidationException;
import federicopini.B6_L5.payloads.AuthRequest;
import federicopini.B6_L5.payloads.AuthResponse;
import federicopini.B6_L5.payloads.NewDipendentePayload;
import federicopini.B6_L5.services.AuthService;
import federicopini.B6_L5.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest body){
        return new AuthResponse(authService.checkCredentialsAndGenerateToken(body));
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated NewDipendentePayload body, BindingResult result){
        if(result.hasErrors()){
            throw new ValidationException(result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return this.dipendenteService.createDipentente(body);
    }

}
