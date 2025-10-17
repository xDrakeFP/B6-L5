package federicopini.B6_L5.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NewNota {
    @NotBlank
    private String note;
}
