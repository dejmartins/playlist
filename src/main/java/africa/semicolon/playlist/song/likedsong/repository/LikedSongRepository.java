package africa.semicolon.playlist.song.likedsong.repository;

import africa.semicolon.playlist.song.likedsong.model.LikedSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
    Optional<LikedSong> findLikedSongByUserEntity_IdAndSong_SongId(Long userEntityId, Long userId);
    List<LikedSong> findAllByUserEntity_Id(Long userEntityId);
}
