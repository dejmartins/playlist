package africa.semicolon.playlist.auth.exceptions;

import africa.semicolon.playlist.exceptions.BusinessLogicException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyUsedException extends BusinessLogicException {

    public UsernameAlreadyUsedException() {
        this("Email address already used");
    }

    public UsernameAlreadyUsedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
