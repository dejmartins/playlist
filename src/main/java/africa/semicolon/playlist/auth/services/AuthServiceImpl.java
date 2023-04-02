package africa.semicolon.playlist.auth.services;

import africa.semicolon.playlist.auth.dtos.requests.LoginRequestDto;
import africa.semicolon.playlist.auth.dtos.requests.SignupRequestDto;
import africa.semicolon.playlist.auth.dtos.responses.TokenResponseDto;
import africa.semicolon.playlist.auth.security.AuthenticatedUser;
import africa.semicolon.playlist.exception.userExceptions.InvalidLoginDetailsException;
import africa.semicolon.playlist.exception.userExceptions.UserAlreadyExistsException;
import africa.semicolon.playlist.auth.security.JwtGenerator;
import africa.semicolon.playlist.user.data.repositories.UserRepository;
import africa.semicolon.playlist.wallet.model.Wallet;
import africa.semicolon.playlist.wallet.repositories.WalletRepository;
import africa.semicolon.playlist.exception.userExceptions.UserNotFoundException;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private WalletRepository walletRepository;
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
        if (userRepository.existsByEmailAddressOrUsername(requestDto.getEmail(), requestDto.getUsername())) {
            throw new UserAlreadyExistsException("Email or Username has already been used");
        }

        Wallet wallet = createWallet();

//        User user = User.builder().build();
        UserEntity userEntity = UserEntity.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .emailAddress(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .wallet(wallet)
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);
        String token = jwtGenerator.generateToken(savedUserEntity);
        return TokenResponseDto.builder().token("Bearer " + token).build();
    }

    @Override
    public UserEntity getCurrentUser() {
        try {
            AuthenticatedUser authenticatedUser =
                    (AuthenticatedUser) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();

            return userRepository.findById(authenticatedUser.getUserEntity().getId())
                    .orElseThrow(UserNotFoundException::new);
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
    }

    private Wallet createWallet() {
        Wallet wallet = Wallet.builder()
                .balance(BigDecimal.valueOf(0))
                .build();

        return walletRepository.save(wallet);
    }


}
