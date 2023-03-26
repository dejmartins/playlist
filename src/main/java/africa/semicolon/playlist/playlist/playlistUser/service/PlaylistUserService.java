package africa.semicolon.playlist.playlist.playlistUser.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import java.util.Set;

public interface PlaylistUserService {

    ApiResponse addPlaylistToUser(PlayList playList);

    ApiResponse removePlaylistFromUser(UserEntity userEntity, PlayList playList);

    Set<PlayList> getPlaylistForUser(UserEntity userEntity);

    Set<UserEntity> getPlaylistUsers(PlayList playList);

    ApiResponse addPlaylistToUser(Long playlistId);

    ApiResponse removePlaylistFromUser(Long playlistId);

    Set<UserEntity> getPlaylistUsers(Long playlistId);
}
