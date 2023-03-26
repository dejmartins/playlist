package africa.semicolon.playlist.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class SongNotFoundException extends PlaylistException {
//    @Serial
//    private static final long serialVersionUID = 1;

    public SongNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
