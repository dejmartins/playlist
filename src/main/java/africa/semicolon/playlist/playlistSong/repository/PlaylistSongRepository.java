package africa.semicolon.playlist.playlistSong.repository;


import africa.semicolon.playlist.playlistSong.demoPlaylistSong.PlaylistUser;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistSongRepository extends CrudRepository<PlaylistUser, Long>{

    List<PlaylistUser> findByUser(UserEntity userEntity);

    List<PlaylistUser> findByPlayList(PlayList playList);
}
