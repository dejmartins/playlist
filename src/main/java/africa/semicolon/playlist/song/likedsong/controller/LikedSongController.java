package africa.semicolon.playlist.song.likedsong.controller;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.likedsong.service.LikedSongService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likedSongs")
@AllArgsConstructor
public class LikedSongController {
    private LikedSongService likedSongService;

    @PostMapping("/liked/{userEntityId}/{songTitle}")
    public ResponseEntity<ApiResponse> likeSong(@PathVariable Long userEntityId, @PathVariable String songTitle) {
        ApiResponse response = likedSongService.likeSong(userEntityId, songTitle);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/likedSong/{userEntityId}/{songTitle}")
    public ResponseEntity<SongResponse> findLikedSong(@PathVariable Long userEntityId, @PathVariable String songTitle) {
        SongResponse songResponse = likedSongService.findALikedSong(userEntityId, songTitle);
        return ResponseEntity.ok(songResponse);
    }
}
