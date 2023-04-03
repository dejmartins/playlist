package africa.semicolon.playlist.song.likedsong.controller;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.likedsong.service.LikedSongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likedSongs")
@AllArgsConstructor
public class LikedSongController {
    private LikedSongService likedSongService;

    @PostMapping("/like")
    @Operation(summary = "to like a song")
    public ResponseEntity<ApiResponse> likeSong(
            @Parameter(name = "songId", description = "The ID of the song to like", required = true)
            @RequestParam @NotNull Long songId) {
        ApiResponse response = likedSongService.likeSong(songId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{songId}")
    @Operation(summary = "to get a song")
    public ResponseEntity<SongResponse> findLikedSong(
            @Parameter(name = "songId", description = "The ID of the liked song to find", required = true)
            @PathVariable @NotNull Long songId) {
        SongResponse songResponse = likedSongService.findALikedSong(songId);
        return ResponseEntity.ok(songResponse);
    }

    @GetMapping("")
    @Operation(summary = "to get all liked songs by a user")
    public ResponseEntity<Page<SongResponse>> findAllLikedSongsByAUser(
            @Parameter(name="pageNumber", description = "The number of page to return")
            @RequestParam int pageNumber) {
        Page<SongResponse> songResponses = likedSongService.findAllLikedSongsByUser(pageNumber);
        return ResponseEntity.ok(songResponses);
    }

    @PostMapping("/{songId}")
    @Operation(summary = "to dislike a song")
    public ResponseEntity<ApiResponse> dislikeSong(
            @Parameter(name = "songId", description="The id of the song to dislike", required = true)
            @PathVariable @NotNull Long songId) {
        ApiResponse response = likedSongService.dislikeSong(songId);
        return ResponseEntity.ok(response);
    }
}
