package africa.semicolon.playlist.exceptions;

import org.springframework.http.HttpStatus;

public class PlaylistNotFoundException extends BusinessLogicException {

    public PlaylistNotFoundException() {
        this("Playlist not found!");
    }

    public PlaylistNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
