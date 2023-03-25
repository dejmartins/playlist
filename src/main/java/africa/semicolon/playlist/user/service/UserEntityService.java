package africa.semicolon.playlist.user.service;

import africa.semicolon.playlist.user.dto.response.request.RegistrationRequest;
import africa.semicolon.playlist.user.dto.response.request.RegistrationResponse;

public interface UserEntityService {
    RegistrationResponse registerUser(RegistrationRequest request);
}
