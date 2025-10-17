package federicopini.B6_L5.exceptions;

import java.util.List;

public class AlreadyCompletedException extends RuntimeException {
    public AlreadyCompletedException(String message) {
        super(message);
    }
}
