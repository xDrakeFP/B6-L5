package federicopini.B6_L5.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorsMessages;

    public ValidationException(List<String> errorsMessages) {
        super("Ci sono stati errori di validazione");
        this.errorsMessages = errorsMessages;
    }
}
