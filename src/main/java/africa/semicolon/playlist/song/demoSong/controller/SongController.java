package africa.semicolon.playlist.song.demoSong.controller;

import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/songs")
public class SongController {
    private final SongService songService;

    @GetMapping("search")
    public ResponseEntity<SongResponse> searchSong(@RequestParam String songTitle) {
        SongResponse response = songService.searchSong(songTitle);
        return ResponseEntity.ok(response);
    }

}
