package africa.semicolon.playlist.spotify;


import africa.semicolon.playlist.song.demoSong.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/song/{trackName}")
    public ResponseEntity<?> searchForTrack(@PathVariable String trackName) throws Exception {
        Song result = spotifyService.findingSong(trackName);
        return ResponseEntity.ok().body(result);
    }

}

