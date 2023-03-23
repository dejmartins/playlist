package africa.semicolon.playlist.song.likedsong;

import africa.semicolon.playlist.song.demoSong.Song;
import africa.semicolon.playlist.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Song song;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity userEntity;
    private boolean liked;
    private boolean dislike;

}
