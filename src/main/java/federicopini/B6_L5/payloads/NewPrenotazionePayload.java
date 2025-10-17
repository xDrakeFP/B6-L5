package federicopini.B6_L5.payloads;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;


@Getter
@ToString
@AllArgsConstructor
public class NewPrenotazionePayload {
    @NotNull
    private UUID dipendenteId;
    @NotNull
    private UUID viaggioId;
    private String note;
}
