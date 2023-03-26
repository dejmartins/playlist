package africa.semicolon.playlist.playlist.playlistUser.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.playlist.playlistUser.repository.PlaylistUserRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistUserServiceImpl implements PlaylistUserService {

    private final PlaylistUserRepository pluRepository;


    @Override
    public ApiResponse addPlaylistToUser(UserEntity userEntity, PlayList playList) {
        return null;
    }

    @Override
    public ApiResponse removePlaylistFromUser(UserEntity userEntity, PlayList playList) {
        return null;
    }

    @Override
    public Set<PlayList> getPlaylistForUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public Set<UserEntity> getPlaylistUsers(PlayList playList) {
        return null;
    }
}
