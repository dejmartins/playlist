package africa.semicolon.playlist.exception;

import org.springframework.http.HttpStatus;

public class ContributorNotFoundException extends PlaylistException {

    public ContributorNotFoundException() {
        this("Playlist_User not found!");
    }

    public ContributorNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
