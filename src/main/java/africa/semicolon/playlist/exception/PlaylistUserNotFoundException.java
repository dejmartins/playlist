package africa.semicolon.playlist.exception;

import org.springframework.http.HttpStatus;

public class PlaylistUserNotFoundException extends PlaylistException {

    public PlaylistUserNotFoundException() {
        this("Playlist_User not found!");
    }

    public PlaylistUserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
