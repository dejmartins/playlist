package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.request.UpdateUserRequestDto;
import africa.semicolon.playlist.user.dto.response.RegistrationResponse;
import africa.semicolon.playlist.user.dto.response.UserDto;

public interface UserEntityService {
    UserEntity privateFindUserById(Long userId);

    UserEntity privateFindUserByUsername(String username);
    UserEntity getUserById(Long userId);

    UserDto getUserDetails();

    UserDto updateUserDetails(UpdateUserRequestDto request);

}
