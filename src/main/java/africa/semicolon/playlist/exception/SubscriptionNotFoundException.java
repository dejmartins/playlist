package africa.semicolon.playlist.exception;

import org.springframework.http.HttpStatus;

public class SubscriptionNotFoundException extends PlaylistException {

    public SubscriptionNotFoundException() {
        this("Subscriber not found exception.");
    }

    public SubscriptionNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
