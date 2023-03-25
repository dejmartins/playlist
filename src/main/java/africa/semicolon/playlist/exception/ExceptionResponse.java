package africa.semicolon.playlist.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@Data
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private HttpStatus status;
    private Map<String, String> data;

}
