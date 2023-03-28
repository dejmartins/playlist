package africa.semicolon.playlist;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder
public class ApiResponse {
    private HttpStatus status;
    private String message;

}
