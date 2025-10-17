package federicopini.B6_L5.payloads;

import federicopini.B6_L5.entities.enums.StatoViaggio;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString

public class NewViaggioPayload {
    @NotBlank
    private String destinazione;
    @NotNull
    @Future
    private LocalDate data;
    @NotNull
    private StatoViaggio statoViaggio;
}
