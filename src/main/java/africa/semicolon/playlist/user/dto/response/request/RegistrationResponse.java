package africa.semicolon.playlist.user.dto.response.request;

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
