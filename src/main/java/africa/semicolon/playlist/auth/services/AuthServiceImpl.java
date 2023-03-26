package africa.semicolon.playlist.auth.services;

import africa.semicolon.playlist.auth.dtos.requests.LoginRequestDto;
import africa.semicolon.playlist.auth.dtos.requests.SignupRequestDto;
import africa.semicolon.playlist.auth.dtos.responses.TokenResponseDto;
import africa.semicolon.playlist.exception.userExceptions.InvalidLoginDetailsException;
import africa.semicolon.playlist.exception.userExceptions.UserAlreadyExistsException;
import africa.semicolon.playlist.auth.security.JwtGenerator;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;
    private AuthenticationManager authenticationManager;


    @Override
    public TokenResponseDto login(LoginRequestDto requestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                            requestDto.getPassword())
            );
            String token = jwtGenerator.generateToken(authentication);
            return TokenResponseDto.builder().token("Bearer " + token).build();
        } catch (Exception e) {
            throw new InvalidLoginDetailsException();
        }
    }

    @Override
    public TokenResponseDto createAccount(SignupRequestDto requestDto) {
        if (userRepository.existsByEmailAddress(requestDto.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .emailAddress(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);
        String token = jwtGenerator.generateToken(savedUserEntity);
        return TokenResponseDto.builder().token("Bearer " + token).build();
    }
}
