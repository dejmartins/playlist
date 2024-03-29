package africa.semicolon.playlist.song.playlistSong.repository;


import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.song.playlistSong.demo.PlaylistSongEntity;
import africa.semicolon.playlist.song.demoSong.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSongEntity, Long> {

    Optional<PlaylistSongEntity> findByPlayListAndSong(PlayList playList, Song song);

    Optional<List<PlaylistSongEntity>> findByPlayList(PlayList playList);


}
