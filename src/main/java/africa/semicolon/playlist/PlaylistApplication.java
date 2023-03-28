package africa.semicolon.playlist;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;



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
