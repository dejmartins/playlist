package africa.semicolon.playlist;

//<<<<<<< Updated upstream
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import africa.semicolon.playlist.configExtra.RestTemplateConfig;
import org.springframework.context.annotation.Import;


@Import(RestTemplateConfig.class)
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Playlist API", version = "v0.0.1-SNAPSHOT")
)
@ConfigurationPropertiesScan
public class PlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistApplication.class, args);
	}

}
