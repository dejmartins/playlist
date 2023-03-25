package africa.semicolon.playlist.playlistUser.repository;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlistUser.demoPlaylistUser.PlaylistUser;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistUserRepository extends JpaRepository<PlaylistUser, Long> {

    Optional<List<PlaylistUser>> findByUser(UserEntity userEntity);

    Optional<List<PlaylistUser>> findByPlayList(PlayList playList);

}
