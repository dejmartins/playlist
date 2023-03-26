package africa.semicolon.playlist.exception.bankAccountDetailsExceptions;

import africa.semicolon.playlist.exception.PlaylistException;
import org.springframework.http.HttpStatus;

public class BankAccountDetailsAlreadyExistsException extends PlaylistException {

    public BankAccountDetailsAlreadyExistsException() {
        this("Bank Account Details Used Already");
    }

    public BankAccountDetailsAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
