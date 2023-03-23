package africa.semicolon.playlist.spotify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Track;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/spotify")
public class ControllerSpotify {

    private final SpotifyService spotifyService;

    public ControllerSpotify(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Track>> searchTracks(@RequestParam String query) {
        try {
            ResponseEntity<String> response = spotifyService.searchTrack(query, 1, 0);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            JsonNode tracksNode = jsonNode.get("tracks").get("items");
            List<Track> tracks = objectMapper.convertValue(tracksNode, new TypeReference<>() {
            });
            return ResponseEntity.ok(tracks);
        } catch (IOException e) {
            // handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

