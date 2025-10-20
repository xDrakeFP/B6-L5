package federicopini.B6_L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest (@Email @NotBlank String email, @NotBlank String password){
}
