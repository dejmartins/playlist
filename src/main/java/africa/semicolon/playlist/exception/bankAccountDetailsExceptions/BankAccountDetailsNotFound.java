package africa.semicolon.playlist.exception.bankAccountDetailsExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class BankAccountDetailsNotFound extends PlaylistException {

    public BankAccountDetailsNotFound() {
        this("Bank Account Details not found");
    }

    public BankAccountDetailsNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
