package africa.semicolon.playlist.playlist.playlistUser.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.exception.PlaylistUserNotFoundException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.playlistUser.demoPlaylistUser.PlaylistUser;
import africa.semicolon.playlist.playlist.playlistUser.repository.PlaylistUserRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistUserServiceImpl implements PlaylistUserService {

    private final PlaylistUserRepository pluRepository;


    @Override
    public ApiResponse addPlaylistToUser(PlayList playList) {
        PlaylistUser newPlaylistUser = PlaylistUser.builder()
                .user(userEntity)
                .playList(playList)
                .build();
        pluRepository.save(newPlaylistUser);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse removePlaylistFromUser(UserEntity userEntity, PlayList playList) {
        PlaylistUser foundPlaylistUser = pluRepository.findByUserAndPlayList(userEntity, playList).orElseThrow(PlaylistUserNotFoundException::new);
        pluRepository.delete(foundPlaylistUser);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Set<PlayList> getPlaylistForUser(UserEntity userEntity) {
        List<PlaylistUser> foundPlaylistUser = pluRepository.findByUser(userEntity).orElseThrow(PlaylistUserNotFoundException::new);
        Set<PlayList> userPlaylists = new HashSet<>();
        for (PlaylistUser playlistUser : foundPlaylistUser) {
            userPlaylists.add(playlistUser.getPlayList());
        }
        return userPlaylists;
    }

    @Override
    public Set<UserEntity> getPlaylistUsers(PlayList playList) {
        List<PlaylistUser> foundPlaylistUser = pluRepository.findByPlayList(playList).orElseThrow(PlaylistUserNotFoundException::new);
        Set<UserEntity> users = new HashSet<>();
        for (PlaylistUser playlistUser : foundPlaylistUser) {
            users.add(playlistUser.getUser());
        }
        return users;
    }


}
