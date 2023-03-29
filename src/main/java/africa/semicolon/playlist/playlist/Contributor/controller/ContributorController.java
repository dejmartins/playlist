package africa.semicolon.playlist.playlist.Contributor.controller;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/contributor")
public class ContributorController {

    private final ContributorService contributorService;

    @Autowired
    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }


    @PostMapping("/addContrib")
    @Operation(summary = "Add a contributor to a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    public ResponseEntity<?> addContributorToPlaylist(
            @RequestParam
            @Parameter(name = "username", description = "The username of the User to be made a contributor",
                    required = true, example = "apex") @Valid @NotNull
            String username,
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = contributorService.addContributorToPlaylist(username, playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/removeContrib")
    @Operation(summary = "Remove a contributor from a playlist",
            description = "Returns a Response entity containing the message and HTTP status code")
    public ResponseEntity<?> removeContributor(
            @RequestParam
            @Parameter(name = "username", description = "The username of the User to be removed from contributor",
                    required = true, example = "apex") @Valid @NotNull
            String username,
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = contributorService.removeContributor(username, playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getPlaylists")
    @Operation(summary = "Get playlists for a user",
            description = "Returns a Response entity containing a set of playlists and HTTP status code")
    public ResponseEntity<?> getPlaylistForUser() {
        Set<PlayList> response = contributorService.getPlaylistForUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get contributors for a playlist",
            description = "Returns a Response entity containing a set of UserEntities and HTTP status code")
    public ResponseEntity<?> getPlaylistContributors(@RequestParam
                                                     @Parameter(name = "playlistId", description = "The id of the required playlist",
                                                             required = true, example = "1L") @Valid @NotNull
                                                     Long playlistId) {
        Set<UserEntity> response = contributorService.getPlaylistContributors(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}