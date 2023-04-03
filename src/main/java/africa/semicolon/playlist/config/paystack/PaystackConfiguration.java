package africa.semicolon.playlist.config.paystack;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "paystack")
public class PaystackConfiguration {

    private String secretKey;
    private String publicKey;
}
