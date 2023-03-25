package africa.semicolon.playlist.playlistUser.service;

import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.playlistUser.repository.PlaylistUserRepository;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaylistUserServiceImpl implements PlaylistUserService {

    private final PlaylistService playlistService;
    private final PlaylistUserRepository pluRepository;


}
