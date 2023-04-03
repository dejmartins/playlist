package africa.semicolon.playlist.exception;


import org.springframework.http.HttpStatus;

public class PlaylistSongNotFoundException extends PlaylistException {

    public PlaylistSongNotFoundException() {
        this("Playlist_Song not found!");
    }

    public PlaylistSongNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
