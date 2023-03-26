package africa.semicolon.playlist.auth.services;

import africa.semicolon.playlist.auth.dtos.requests.LoginRequestDto;
import africa.semicolon.playlist.auth.dtos.requests.SignupRequestDto;
import africa.semicolon.playlist.auth.dtos.responses.TokenResponseDto;
import africa.semicolon.playlist.user.data.models.UserEntity;

public interface AuthService {

    TokenResponseDto login(LoginRequestDto requestDto);

    TokenResponseDto createAccount(SignupRequestDto requestDto);

    UserEntity getCurrentUser();

}
