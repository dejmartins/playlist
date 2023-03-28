package africa.semicolon.playlist.playlistSong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.exception.PlaylistNotFoundException;
import africa.semicolon.playlist.exception.PlaylistSongNotFoundException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.playlistSong.demo.PlaylistSongEntity;
import africa.semicolon.playlist.song.playlistSong.repository.PlaylistSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PlaylistSongServiceImpl implements PlaylistSongService {
    private final PlaylistSongRepository playlistSongRepository;


    @Override
    public ApiResponse addSongToPlayList(PlayList playList, Song song) {
        PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                .song(song)
                .playList(playList)
                .build();
        playlistSongRepository.save(playlistSongEntity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse addSongsToPlayList(PlayList playList, List<Song> songs) {
        Set<PlaylistSongEntity> playlistSongEntitySet = new HashSet<>();

        for (Song aSong : songs) {
            PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                    .playList(playList)
                    .song(aSong)
                    .build();
            playlistSongEntitySet.add(playlistSongEntity);
        }

        playlistSongRepository.saveAll(playlistSongEntitySet);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse deleteSongFromPlayList(PlayList playList, Song song) {
        PlaylistSongEntity playlistSongEntity = playlistSongRepository.findByPlayListAndSong(playList, song).orElseThrow(PlaylistNotFoundException :: new);
        playlistSongRepository.delete(playlistSongEntity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public Set<Song> getSongsInPlaylist(PlayList playList) {
        List<PlaylistSongEntity> playlistSongEntity = playlistSongRepository.findByPlayList(playList).orElseThrow(PlaylistSongNotFoundException :: new);
        Set<Song> songsInPlaylist = new HashSet<>();
        for (PlaylistSongEntity aPlaylistSongEntity : playlistSongEntity) {
            songsInPlaylist.add(aPlaylistSongEntity.getSong());
        }
        return songsInPlaylist;
    }
}
