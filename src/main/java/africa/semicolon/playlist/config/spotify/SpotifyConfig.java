package africa.semicolon.playlist.config.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spotify.client")
public class SpotifyConfig {

    private String clientId;

    private String clientSecret;

}
