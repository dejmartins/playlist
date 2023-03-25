package africa.semicolon.playlist.song.demoSong;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    private String spotifyId;
    private String playBack;
    private String title;
    private String artiste;
    private String image;
    private boolean explicit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String albumName;
    private String duration;
    private String externalHref;

}
