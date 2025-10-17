package federicopini.B6_L5.payloads;

import federicopini.B6_L5.entities.enums.StatoViaggio;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString

public class NewViaggioPayload {
    @NotBlank
    private String destinazione;
    @NotBlank
    private LocalDate data;
    private StatoViaggio statoViaggio;
}
