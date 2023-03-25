package africa.semicolon.playlist.song.likedsong.repository;

import africa.semicolon.playlist.song.likedsong.LikedSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
}
