package africa.semicolon.playlist.auth.dtos.requests;

import africa.semicolon.playlist.config.utilities.RegexPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = RegexPatterns.USERNAME, message = "Invalid characters found in username")
    @Length(min = 6, max = 20, message = "Username should be between 6 - 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
