package africa.semicolon.playlist.playlistSong.demoPlaylistSong;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PlayList playList;
}

