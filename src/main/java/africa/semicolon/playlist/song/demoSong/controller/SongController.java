package africa.semicolon.playlist.song.demoSong.controller;

import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    private final SongService songService;

    @GetMapping("search")
    @Operation(summary = "To search songs")
    public ResponseEntity<SongResponse> searchSong(
            @Parameter(name = "songTitle", description = "The title of the song to search", required = true)
            @RequestParam @NotBlank String songTitle) {
        SongResponse response = songService.searchSong(songTitle);
        return ResponseEntity.ok(response);
    }

}
