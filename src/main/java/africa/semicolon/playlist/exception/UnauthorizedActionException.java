package africa.semicolon.playlist.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedActionException extends PlaylistException {

    public UnauthorizedActionException() {
        this("You are not allowed to perform this action");
    }

    public UnauthorizedActionException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
