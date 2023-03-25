package africa.semicolon.playlist.exception.userExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends PlaylistException {

    public UserAlreadyExistsException() {
        this("Email address already used");
    }

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
