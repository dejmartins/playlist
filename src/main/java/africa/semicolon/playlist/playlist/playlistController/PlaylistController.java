package africa.semicolon.playlist.playlist.playlistController;


import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.PlaylistException;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import africa.semicolon.playlist.playlist.dto.UpdatePlaylistDetailsRequest;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("api/v1/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Operation(summary = "Create a new playlist",
            description = "Returns a Response entity containing the saved playlist and HTTP status code")
    @PostMapping("/create")
    public ResponseEntity<CreatePlaylistResponse> createPlaylist(
            @RequestBody
            @Parameter(name = "CreatePlaylistRequest", description = "The details required to create a playlist which are name, slug, description and isPublic",
                    required = true) @Valid
            CreatePlaylistReq playlistReq) {
        CreatePlaylistResponse foundPlaylist = playlistService.createPlaylist(playlistReq);
        return new ResponseEntity<>(foundPlaylist, HttpStatus.OK);
    }


    @Operation(summary = "Get A Particular Playlist by the playlist's slug",
            description = "Returns a Response entity containing the requested playlist and HTTP status code")
    @GetMapping("/find")
    public ResponseEntity<FindPlaylistResponse> getPlaylistBySlug(
            @RequestParam
            @Parameter(name = "slug", description = "The slug of the required playlist",
                    required = true, example = "Awake-vibes") @Valid @NotNull
            String slug) {
        FindPlaylistResponse foundPlaylist = playlistService.findPlaylistBySlug(slug);
        return new ResponseEntity<>(foundPlaylist, HttpStatus.OK);
    }

    /*
    @Operation(summary = "Get A Particular Playlist by the playlist's id",
            description = "Returns a Response entity containing the requested playlist and HTTP status code")
    @DeleteMapping("/find")
    public ResponseEntity<?> getPlaylistById(
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the required playlist",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        FindPlaylistResponse foundPlaylist = playlistService.findPlaylistById(playlistId);
        return new ResponseEntity<>(foundPlaylist, HttpStatus.OK);
    }

    @Operation(summary = "Get A Particular Playlist by the playlist's id",
            description = "Returns an ApiResponse Response entity containing the operation details")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePlaylistById(
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the required playlist to be deleted",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = playlistService.deletePlaylistById(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     */

    @Operation(summary = "Get A Particular Playlist by the playlist's id",
            description = "Returns an ApiResponse Response entity containing the operation details")
    @DeleteMapping("/delete/{playlistId}")
    public ResponseEntity<ApiResponse> deletePlaylistBySlug(
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the required playlist to be deleted",
                    required = true, example = "1L") @Valid @NotNull
            Long playlistId) {
        ApiResponse response = playlistService.deletePlaylistById(playlistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Update A Particular Playlist's details",
            description = "Returns a Response entity containing the updated playlist and HTTP status code")
    @PatchMapping(value = "/update")
    public ResponseEntity<FindPlaylistResponse> updatePlaylist(@RequestBody
                                                @Parameter(name = "UpdatePlaylistDetailsRequest", description = "Contains the required credentials to create a playlist as well as the id of the playlist to be updated. ",
                                                        required = true)
                                            UpdatePlaylistDetailsRequest request){
        FindPlaylistResponse response = playlistService.updatePlaylistDetails(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update A Particular Playlist's cover image",
            description = "Returns an ApiResponse Response entity containing the operation details")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadPlaylistImage(@RequestParam(value = "file") MultipartFile file,
                                                 @RequestParam
                                                 @Parameter(name = "playlistId", description = "The id of the required playlist whose image is to be changed",
                                                         required = true, example = "1L") @Valid @NotNull
                                                 Long playlistId){
        try {
            ApiResponse response = playlistService.updatePlaylistImage(file, playlistId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (PlaylistException exception){
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
    }
}
