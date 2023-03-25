package africa.semicolon.playlist.auth.services.impl;

import africa.semicolon.playlist.auth.dtos.requests.LoginRequestDto;
import africa.semicolon.playlist.auth.dtos.requests.SignupRequestDto;
import africa.semicolon.playlist.auth.dtos.responses.TokenResponseDto;
import africa.semicolon.playlist.auth.security.JwtGenerator;
import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.userExceptions.InvalidLoginDetailsException;
import africa.semicolon.playlist.exception.userExceptions.UserAlreadyExistsException;
import africa.semicolon.playlist.user.models.User;
import africa.semicolon.playlist.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
class AuthServiceImplTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtGenerator jwtGenerator;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loginTest() {
        var loginRequest = LoginRequestDto.builder()
                .email("john@email.com")
                .password("password")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(jwtGenerator.generateToken(authentication)).thenReturn("eJyuwrtwe");

        TokenResponseDto response = authService.login(loginRequest);
        assertNotNull(response.getToken());
        assertTrue(StringUtils.startsWith(response.getToken(), "Bearer "));
    }

    @Test
    void failedLoginTest() {
        var loginRequest = LoginRequestDto.builder()
                .email("john@email.com")
                .password("password")
                .build();

        when(authenticationManager.authenticate(any())).thenThrow(InvalidLoginDetailsException.class);

        assertThrows(InvalidLoginDetailsException.class, () -> authService.login(loginRequest));
    }

    @Test
    void createAccountTest() {
        var requestDto = SignupRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("password")
                .build();

        var savedUser = User.builder()
                .id(1L)
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .emailAddress(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        when(userRepository.save(any())).thenReturn(savedUser);
        TokenResponseDto response = authService.createAccount(requestDto);
        assertNotNull(response.getToken());
        assertTrue(StringUtils.startsWith(response.getToken(), "Bearer "));
    }

    @Test
    void createAccountFailTest() {
        var requestDto = SignupRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@email.com")
                .password("password")
                .build();


        when(userRepository.existsByEmailAddress(requestDto.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> authService.createAccount(requestDto));
    }
}
