package africa.semicolon.playlist.exception.userExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends PlaylistException {

    public UserNotFoundException() {
        this("User not found");
    }

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
