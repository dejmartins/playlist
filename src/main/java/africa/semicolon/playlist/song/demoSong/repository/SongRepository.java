package africa.semicolon.playlist.song.demoSong.repository;

import africa.semicolon.playlist.song.demoSong.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}