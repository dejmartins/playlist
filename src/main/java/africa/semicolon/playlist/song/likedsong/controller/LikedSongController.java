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

    @PostMapping("/like/{userEntityId}/{songTitle}")
    public ResponseEntity<ApiResponse> likeSong(@PathVariable Long userEntityId, @PathVariable String songTitle) {
        ApiResponse response = likedSongService.likeSong(userEntityId, songTitle);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/likedSong/{userEntityId}/{songTitle}")
    public ResponseEntity<SongResponse> findLikedSong(@PathVariable Long userEntityId, @PathVariable String songTitle) {
        SongResponse songResponse = likedSongService.findALikedSong(userEntityId, songTitle);
        return ResponseEntity.ok(songResponse);
    }
    @GetMapping("/findAll/{userEntity}/{songTitle}/{pageNumber}")
    public ResponseEntity<Page<SongResponse>> findAllLikedSongsByAUser(
            @PathVariable Long userEntity, @PathVariable String songTitle, @PathVariable int pageNumber) {
        Page<SongResponse> songResponses = likedSongService.findAllLikedSongsByUser(userEntity, songTitle, pageNumber);
        return ResponseEntity.ok(songResponses);
    }
    @PostMapping("/song/dislike/{userEntityId}/{songTitle}")
    public ResponseEntity<ApiResponse> dislikeSong(@PathVariable Long userEntityId, @PathVariable String songTitle) {
        ApiResponse response = likedSongService.dislikeSong(userEntityId, songTitle);
        return ResponseEntity.ok(response);
    }
}
