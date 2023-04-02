package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.PlaylistException;
import africa.semicolon.playlist.exception.userExceptions.UserNotFoundException;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.data.repositories.UserRepository;
import africa.semicolon.playlist.user.dto.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.request.UpdateUserRequestDto;
import africa.semicolon.playlist.user.dto.response.RegistrationResponse;
import africa.semicolon.playlist.user.dto.response.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final ModelMapper modelMapper;


    @Override
    public UserEntity privateFindUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity privateFindUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User could not be found"));
    }

    @Override
    public UserDto getUserDetails() {
        UserEntity user = authService.getCurrentUser();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserDetails(UpdateUserRequestDto request) {
        UserEntity user = authService.getCurrentUser();
        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), user.getId()))
            throw new PlaylistException("Username is not available", HttpStatus.BAD_REQUEST);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

}
