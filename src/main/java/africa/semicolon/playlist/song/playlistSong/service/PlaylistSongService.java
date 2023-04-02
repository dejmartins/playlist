package africa.semicolon.playlist.song.playlistSong.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.song.demoSong.model.Song;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistSongService {

    ApiResponse addSongToPlayList(PlayList playList, Song song);

    ApiResponse addSongsToPlayList(PlayList playList, List<Song> songs);

    ApiResponse deleteSongFromPlayList(PlayList playList, Song song);

    PageDto<Song> getSongsInPlaylist(PlayList playList, Pageable pageable);

    ApiResponse addSongToPlayList(Long playlistId, Long songId);

    ApiResponse addSongsToPlayList(Long playlistId, List<Song> songs);

    ApiResponse deleteSongFromPlayList(Long playlistId, Long songId);

    PageDto<Song> getSongsInPlaylist(Long playlistId, Pageable pageable);

}
