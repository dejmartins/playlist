package africa.semicolon.playlist.user.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String message;
    private boolean success;
}
