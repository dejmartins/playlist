package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.response.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.response.request.RegistrationResponse;
import africa.semicolon.playlist.user.repository.UserEntityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;


    @Override
    public RegistrationResponse registerUser(@Valid RegistrationRequest request) {
        UserEntity user = modelMapper.map(request, UserEntity.class);
        return RegistrationResponse.builder()
                .message("Registration successful")
                .success(true)
                .build();
    }

}
