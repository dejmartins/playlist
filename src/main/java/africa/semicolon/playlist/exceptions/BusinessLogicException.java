package africa.semicolon.playlist.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessLogicException extends RuntimeException {

    private HttpStatus status;

    public BusinessLogicException() {
        this("An error occurred");
    }

    public BusinessLogicException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessLogicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
