package africa.semicolon.playlist.song.demoSong.model;

import africa.semicolon.playlist.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Song song;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AppUser appUser;
    private boolean liked;
    private boolean dislike;
}
