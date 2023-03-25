package africa.semicolon.playlist.exceptions;

import org.springframework.http.HttpStatus;

public class PlaylistSongNotFoundException extends BusinessLogicException {

    public PlaylistSongNotFoundException() {
        this("Playlist_Song not found!");
    }

    public PlaylistSongNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
