package africa.semicolon.playlist.song.likedsong;

import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.user.data.models.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likedSongId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Song song;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserEntity userEntity;
    private boolean liked;
    private boolean dislike;

}
