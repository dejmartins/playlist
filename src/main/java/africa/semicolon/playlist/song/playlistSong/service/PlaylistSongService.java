package africa.semicolon.playlist.song.playlistSong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.song.demoSong.Song;
import africa.semicolon.playlist.song.playlistSong.demo.PlaylistSongEntity;

import java.util.List;
import java.util.Set;

public interface PlaylistSongService {

    ApiResponse addSongToPlayList(PlayList playList, Song song);

    ApiResponse addSongsToPlayList(PlayList playList, List<Song> songs);

    ApiResponse deleteSongFromPlayList(PlayList playList, Song song);

    Set<Song> getSongsInPlaylist(PlayList playList);

}
