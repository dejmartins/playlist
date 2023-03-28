package africa.semicolon.playlist.song.likedsong.controller;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.likedsong.service.LikedSongService;
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
    public ResponseEntity<ApiResponse> likeSong(@RequestParam Long userEntityId, @RequestParam String songTitle) {
        ApiResponse response = likedSongService.likeSong(userEntityId, songTitle);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/likedSong")
    public ResponseEntity<SongResponse> findLikedSong(@RequestParam Long userEntityId, @RequestParam String songTitle) {
        SongResponse songResponse = likedSongService.findALikedSong(userEntityId, songTitle);
        return ResponseEntity.ok(songResponse);
    }
    @GetMapping("/findAll")
    public ResponseEntity<Page<SongResponse>> findAllLikedSongsByAUser(
            @RequestParam Long userEntity, @RequestParam String songTitle, @RequestParam int pageNumber) {
        Page<SongResponse> songResponses = likedSongService.findAllLikedSongsByUser(userEntity, songTitle, pageNumber);
        return ResponseEntity.ok(songResponses);
    }
    @PostMapping("/dislike")
    public ResponseEntity<ApiResponse> dislikeSong(@RequestParam Long userEntityId, @RequestParam String songTitle) {
        ApiResponse response = likedSongService.dislikeSong(userEntityId, songTitle);
        return ResponseEntity.ok(response);
    }
}
