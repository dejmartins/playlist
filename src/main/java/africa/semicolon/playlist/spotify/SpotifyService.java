package africa.semicolon.playlist.spotify;

import africa.semicolon.playlist.song.demoSong.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;


@Service
public class SpotifyService {

    public Song findingSong(String songName) throws JsonProcessingException {
        String path = "C:\\Users\\user\\IdeaProjects\\playlist\\src\\main\\resources\\secret.properties";
        FileInputStream fileInput;
        try {
            fileInput = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties properties = new Properties();
        try {
            properties.load(fileInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String clientId = properties.getProperty("client-id");
        String clientSecret = properties.getProperty("client-secret");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headersOne = new HttpHeaders();
        headersOne.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String originalString = clientId + ":" + clientSecret;
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes(StandardCharsets.UTF_8));
        String encodedString = new String(encodedBytes, StandardCharsets.UTF_8);
        headersOne.set("Authorization", "Basic " + encodedString);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headersOne);

        ResponseEntity<String> responseOne = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, String.class);

        JSONObject jsonObj = new JSONObject(responseOne.getBody());
        String accessToken = jsonObj.getString("access_token");

        RestTemplate restTemplateToFindSong = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
//        String accessToken = "BQAQe9D2bGb0nN9bLmuUoyVZzkaF8d37vgYIKUUJDprxvvAt0n13CHBnI5OhIkU-9pW3uyHn7_7Qz7UQv1UWD6dinoL4ImMPCQeRVjIWTOAei5a6ez87";
        headers.set("Authorization", "Bearer " + accessToken);

        String url = "https://api.spotify.com/v1/search?q=" + songName + "&type=track";

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplateToFindSong.exchange(
                url, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode tracks = root.path("tracks").path("items");

        String title = tracks.get(0).get("name").asText();
        String spotifyId = tracks.get(0).get("id").asText();
        String artiste = getArtists(tracks.get(0).get("album").get("artists"));
        String image = tracks.get(0).get("album").get("images").get(1).get("url").asText();
        String explicit = tracks.get(0).get("explicit").asText();
        boolean isExplicit = explicit.equals("false");
        Date releaseDate;
        try {
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(tracks.get(0).get("album").get("release_date").asText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String albumName = tracks.get(0).get("album").get("name").asText();
        String duration = tracks.get(0).get("duration_ms").asText();
        String externalHref = tracks.get(0).get("external_urls").get("spotify").asText();
        String playBack = tracks.get(0).get("preview_url").asText();

        return Song.builder()
                .spotifyId(spotifyId)
                .playBack(playBack)
                .title(title)
                .artiste(artiste)
                .image(image)
                .explicit(isExplicit)
                .releaseDate(releaseDate)
                .albumName(albumName)
                .duration(duration)
                .externalHref(externalHref)
                .build();
    }

    private static String getArtists(JsonNode artistsNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode artists = mapper.readTree(artistsNode.toString());

        StringBuilder artistsString = new StringBuilder();

        // iterate through the array and extract artist names
        for (JsonNode artist : artists) {
            artistsString.append(artist.get("name").asText()).append(", ");
        }
        return artistsString.toString();
    }

}
