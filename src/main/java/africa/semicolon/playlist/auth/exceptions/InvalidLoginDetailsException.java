package africa.semicolon.playlist.auth.exceptions;

import africa.semicolon.playlist.exceptions.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class InvalidLoginDetailsException extends BusinessLogicException {

    public InvalidLoginDetailsException() {
        this("Invalid login details");
    }

    public InvalidLoginDetailsException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
