package africa.semicolon.playlist.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public class PlaylistException extends RuntimeException {

    @Getter
    @Setter
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
