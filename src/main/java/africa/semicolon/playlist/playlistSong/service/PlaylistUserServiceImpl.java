package africa.semicolon.playlist.playlistSong.service;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.playlistSong.repository.PlaylistSongRepository;
import africa.semicolon.playlist.song.demoSong.Song;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaylistUserServiceImpl implements PlaylistUserService {

    private final PlaylistService playlistService;
    private final SongService songService;
    private final PlaylistSongRepository pluRepository;

    @Override
    public void addSongToPlaylist() {

    }

    @Override
    public void removeSongFromPlaylist() {

    }
}
