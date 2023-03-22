package africa.semicolon.playlist.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class BusinessLogicExceptionResponse {

    private String message;

    private HttpStatus status;

}
