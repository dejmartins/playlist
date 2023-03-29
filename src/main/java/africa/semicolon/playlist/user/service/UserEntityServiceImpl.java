package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.exception.userExceptions.UserNotFoundException;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.data.repositories.UserRepository;
import africa.semicolon.playlist.user.dto.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.response.RegistrationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public RegistrationResponse registerUser(@Valid RegistrationRequest request) {
        UserEntity user = modelMapper.map(request, UserEntity.class);
//        user.setDateJoined(LocalDateTime.now().toString());
        return RegistrationResponse.builder()
                .message("Registration successful")
                .success(true)
                .build();
    }

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
                .orElseThrow(()-> new UserNotFoundException("User could not be found"));
    }

}
