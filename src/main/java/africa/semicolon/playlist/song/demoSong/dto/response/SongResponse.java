package africa.semicolon.playlist.song.demoSong.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongResponse {
    private Long songId;
    private String playBack;
    private String title;
    private String artiste;
    private String image;
    private boolean explicit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String albumName;
    private String duration;
    private String externalHref;
}
