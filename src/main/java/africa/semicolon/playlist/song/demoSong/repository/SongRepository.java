package africa.semicolon.playlist.song.demoSong.repository;

import africa.semicolon.playlist.song.demoSong.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findSongByTitle(String songTitle);
}
