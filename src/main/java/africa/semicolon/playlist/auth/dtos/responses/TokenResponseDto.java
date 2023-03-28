package africa.semicolon.playlist.auth.dtos.responses;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
    private String token;
}
