package africa.semicolon.playlist.playlist.Contributor.controller;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.Contributor.dto.ContributorDto;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.response.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/contributor")
@Slf4j
public class ContributorController {

    private final ContributorService contributorService;

    @Autowired
    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }


    @PostMapping("/add/{playlistId}/{username}")
    @Operation(summary = "Add a contributor to a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    public ResponseEntity<ContributorDto> addContributorToPlaylist(
            @PathVariable
            @Parameter(name = "username", description = "The username of the User to be made a contributor",
                    required = true, example = "apex")
            String username,
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L")
            Long playlistId) {
        log.info("Request to add contributor to playlist");
        ContributorDto response = contributorService.addContributorToPlaylist(username, playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{playlistId}/{username}")
    @Operation(summary = "Remove a contributor from a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    public ResponseEntity<ApiResponse> removeContributor(
            @PathVariable
            @Parameter(name = "username", description = "The username of the User to be removed from contributor",
                    required = true, example = "apex") @Valid @NotNull
            String username,
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = contributorService.removeContributor(username, playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getPlaylists")
    @Operation(summary = "Get playlists for a user",
            description = "Returns a Response entity containing a set of playlists and HTTP status code")
    public ResponseEntity<Set<PlayList>> getPlaylistForUser() {
        Set<PlayList> response = contributorService.getPlaylistForUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get contributors for a playlist",
            description = "Returns a Response entity containing a set of UserEntities and HTTP status code")
    public ResponseEntity<Set<ContributorDto>> getPlaylistContributors(@RequestParam
                                                     @Parameter(name = "playlistId", description = "The id of the required playlist",
                                                             required = true, example = "1L") @Valid @NotNull
                                                     Long playlistId) {
        Set<ContributorDto> response = contributorService.getPlaylistContributors(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
