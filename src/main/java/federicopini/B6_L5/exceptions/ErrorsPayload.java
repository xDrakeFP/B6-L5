package federicopini.B6_L5.exceptions;
import java.time.LocalDateTime;

public record ErrorsPayload(String message,
                            LocalDateTime timestamp
) {
}
