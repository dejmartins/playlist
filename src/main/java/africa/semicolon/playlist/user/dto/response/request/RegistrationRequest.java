package africa.semicolon.playlist.user.dto.response.request;

import jakarta.validation.constraints.*;

public class RegistrationRequest {
    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be blank")
    @NotEmpty(message = "First Name cannot be empty")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @NotBlank(message = "Last Name cannot be blank")
    @NotEmpty(message = "Last Name cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email emailAddress")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "Invalid email address")
    private String emailAddress;

    @Size(min = 8, max = 25)
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password  cannot be blank")
    @NotEmpty(message = "password  cannot be empty")
    private String password;
}
