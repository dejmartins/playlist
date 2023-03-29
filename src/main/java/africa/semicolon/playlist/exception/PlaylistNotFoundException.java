package africa.semicolon.playlist.exception;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class PlaylistNotFoundException extends PlaylistException {

    public PlaylistNotFoundException() {
        this("Playlist not found!");
    }

    public PlaylistNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
