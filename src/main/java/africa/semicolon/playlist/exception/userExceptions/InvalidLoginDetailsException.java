package africa.semicolon.playlist.exception.userExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class InvalidLoginDetailsException extends PlaylistException {

    public InvalidLoginDetailsException() {
        this("Invalid login details");
    }

    public InvalidLoginDetailsException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
