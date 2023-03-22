package africa.semicolon.playlist.user.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "security.constants")
public class SecurityConstants {
    private long jwtExpTime;
    private String jwtSecretKey;

}
