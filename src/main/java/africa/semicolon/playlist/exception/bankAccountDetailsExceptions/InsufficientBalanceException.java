package africa.semicolon.playlist.exception.bankAccountDetailsExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends PlaylistException {

    public InsufficientBalanceException() {
        this("Not sufficient funds in wallet");
    }

    public InsufficientBalanceException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
