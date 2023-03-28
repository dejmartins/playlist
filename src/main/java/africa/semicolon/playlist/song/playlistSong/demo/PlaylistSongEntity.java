package africa.semicolon.playlist.song.playlistSong.demo;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.song.demoSong.model.Song;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private PlayList playList;
    @ManyToOne
    private Song song;

}
