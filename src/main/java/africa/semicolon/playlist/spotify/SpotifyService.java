package africa.semicolon.playlist.spotify;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;


@Service
public class SpotifyService {

    private final RestTemplate restTemplate;
    private final String spotifyApiBaseUrl;
    private final String spotifyApiVersion;
    private final String spotifyApiClientId;
    private final String spotifyApiClientSecret;

    @Autowired
    public SpotifyService(RestTemplate restTemplate,
                          @Value("${spotify.api.baseurl}") String spotifyApiBaseUrl,
                          @Value("${spotify.api.version}") String spotifyApiVersion,
                          @Value("${spotify.client-id}") String spotifyApiClientId,
                          @Value("${spotify.client-secret}") String spotifyApiClientSecret) {
        this.restTemplate = restTemplate;
        this.spotifyApiBaseUrl = spotifyApiBaseUrl;
        this.spotifyApiVersion = spotifyApiVersion;
        this.spotifyApiClientId = spotifyApiClientId;
        this.spotifyApiClientSecret = spotifyApiClientSecret;
    }

    public ResponseEntity<String> searchTrack(String query, int limit, int offset) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());

        String url = String.format("%s/%s/search?q=%s&type=track&limit=%d&offset=%d",
                spotifyApiBaseUrl, spotifyApiVersion, query, limit, offset);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    private String getAccessToken() {
        String tokenUrl = "https://accounts.spotify.com/api/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(spotifyApiClientId, spotifyApiClientSecret);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(tokenUrl, request, JsonNode.class);

        return response.getBody().get("access_token").asText();
    }
}
