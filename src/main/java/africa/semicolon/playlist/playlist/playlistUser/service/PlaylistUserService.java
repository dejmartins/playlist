package africa.semicolon.playlist.playlist.playlistUser.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import java.util.Set;

public interface PlaylistUserService {

    ApiResponse addPlaylistToUser(UserEntity userEntity, PlayList playList);

    ApiResponse removePlaylistFromUser(UserEntity userEntity, PlayList playList);

    Set<PlayList> getPlaylistForUser(UserEntity userEntity);

    Set<UserEntity> getPlaylistUsers(PlayList playList);
}
