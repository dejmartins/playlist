package africa.semicolon.playlist.playlistSong.repository;


import africa.semicolon.playlist.playlistSong.demoPlaylistSong.PlaylistSong;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PlaylistSongRepository extends CrudRepository<PlaylistSong, Long>{

    List<PlaylistSong> findByUser(UserEntity userEntity);

    List<PlaylistSong> findByPlayList(PlayList playList);
}
