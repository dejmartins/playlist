package africa.semicolon.playlist.song.likedsong.repository;

import africa.semicolon.playlist.song.likedsong.model.LikedSong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
    Optional<LikedSong> findLikedSongBySong_TitleAndUserEntity_Id(String songTitle, Long userEntityId);
    List<LikedSong> findLikedSongByUserEntity_IdAndSong_Title(Long userEntity, String songTitle);
}
