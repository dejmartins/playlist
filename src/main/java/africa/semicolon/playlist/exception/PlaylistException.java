package africa.semicolon.playlist.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PlaylistException extends RuntimeException {

    private HttpStatus status;

    public PlaylistException() {
        this("An error occurred");
    }

    public PlaylistException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public PlaylistException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
