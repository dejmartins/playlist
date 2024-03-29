package africa.semicolon.playlist.song.demoSong.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static africa.semicolon.playlist.config.utilities.PlaylistUtilities.PATTERN;

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
    @JsonFormat(pattern = PATTERN)
    private Date releaseDate;
    private String albumName;
    private String duration;
    private String externalHref;

}
