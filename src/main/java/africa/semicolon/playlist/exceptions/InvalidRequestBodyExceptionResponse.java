package africa.semicolon.playlist.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class InvalidRequestBodyExceptionResponse {

    private Map<String, String> data;

    private String message;

    private HttpStatus status;

    private int statusCode;

}
