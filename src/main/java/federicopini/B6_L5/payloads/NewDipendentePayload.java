package federicopini.B6_L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class NewDipendentePayload {
    @NotBlank
    private String username;
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String avatarURL;
    @NotBlank
    private String password;
}
