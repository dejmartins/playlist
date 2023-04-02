package africa.semicolon.playlist.user.controller;

import africa.semicolon.playlist.user.dto.request.UpdateUserRequestDto;
import africa.semicolon.playlist.user.dto.response.UserDto;
import africa.semicolon.playlist.user.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    @GetMapping("/details")
    @Operation(summary = "Get user details")
    public ResponseEntity<UserDto> getUserDetails() {
        return ResponseEntity.ok(userEntityService.getUserDetails());
    }

    @PatchMapping("/details")
    @Operation(summary = "Update user details")
    public ResponseEntity<UserDto> updateUserDetails(@RequestBody @Valid UpdateUserRequestDto request) {
        return ResponseEntity.ok(userEntityService.updateUserDetails(request));
    }
}
