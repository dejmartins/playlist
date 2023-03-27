package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.response.RegistrationResponse;

public interface UserEntityService {
    RegistrationResponse registerUser(RegistrationRequest request);

    UserEntity privateFindUserById(Long userId);
}
