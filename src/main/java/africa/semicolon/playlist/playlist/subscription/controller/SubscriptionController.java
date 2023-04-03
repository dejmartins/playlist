package africa.semicolon.playlist.playlist.subscription.controller;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.playlist.subscription.service.SubscriberService;
import africa.semicolon.playlist.user.data.models.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/subscription")
public class SubscriptionController {

    private final SubscriberService subscriberService;


    @Autowired
    public SubscriptionController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }


    @Operation(summary = "Subscribe to a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    @PostMapping("/subscribe/{playlistId}")
    public ResponseEntity<ApiResponse> subscribeToPlaylist(
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = subscriberService.subscribeToPlaylist(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Unsubscribe from a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    @DeleteMapping("/unsubscribe/{slug}")
    public ResponseEntity<ApiResponse> unsubscribeToPlaylist(
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            String slug) {
        ApiResponse response = subscriberService.unsubscribeFromPlaylist(slug);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get all subscribers a playlist",
            description = "Returns a Set containing the subscribed users and HTTP status code")
    @GetMapping("/subscribers/{playlistId}")
    public ResponseEntity<PageDto<UserEntity>> getSubscribers(
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId, @ParameterObject Pageable pageable) {
        PageDto<UserEntity> response = subscriberService.getSubscribers(playlistId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get all subscribed to playlists",
            description = "Returns a set containing the playlists and HTTP status code")
    @GetMapping("/getPlaylists")
    public ResponseEntity<PageDto<PlayList>> getSubscribedPlaylists(@ParameterObject Pageable pageable) {
        PageDto<PlayList> response = subscriberService.getSubscribedPlaylists(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/removeUser")
    @Operation(summary = "Remove a subscriber from a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    public ResponseEntity<?> removeContributor(
            @RequestParam
            @Parameter(name = "username", description = "The username of the User to be removed from subscribing",
                    required = true, example = "apex") @Valid @NotNull
            String username,
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = subscriberService.removeUserFromPlaylist(username, playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
