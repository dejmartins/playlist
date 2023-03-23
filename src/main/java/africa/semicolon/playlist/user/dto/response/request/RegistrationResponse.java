package africa.semicolon.playlist.user.dto.response.request;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class RegistrationResponse {
    private String message;
    private boolean success;
}
