package africa.semicolon.playlist.auth.controllers;

import africa.semicolon.playlist.auth.dtos.requests.LoginRequestDto;
import africa.semicolon.playlist.auth.dtos.requests.SignupRequestDto;
import africa.semicolon.playlist.auth.dtos.responses.TokenResponseDto;
import africa.semicolon.playlist.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "CreateNewAccount")
    public ResponseEntity<TokenResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {
        return new ResponseEntity<>(authService.createAccount(requestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
        return new ResponseEntity<>(authService.login(requestDto), HttpStatus.OK);
    }
}
