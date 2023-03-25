package africa.semicolon.playlist.spotify;


import africa.semicolon.playlist.song.demoSong.Song;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/spotify")
public class ControllerSpotify {

    private final SpotifyService spotifyService;

    @Autowired
    public ControllerSpotify(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Track>> searchTracks(@RequestParam String query) {
//        try {
//            ResponseEntity<String> response = spotifyService.searchTrack(query, 1, 0);
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response.getBody());
//            JsonNode tracksNode = jsonNode.get("tracks").get("items");
//            List<Track> tracks = objectMapper.convertValue(tracksNode, new TypeReference<>() {
//            });
//            return ResponseEntity.ok(tracks);
//        } catch (IOException e) {
//            // handle exception
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/song/{trackName}")
    public ResponseEntity<?> searchForTrack(@PathVariable String trackName) throws Exception {
        Song result = spotifyService.findingSong(trackName);
        return ResponseEntity.ok().body(result);
    }

}

