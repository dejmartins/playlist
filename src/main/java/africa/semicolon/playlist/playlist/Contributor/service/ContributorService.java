package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import java.util.Set;

public interface ContributorService {

    ApiResponse addUserToPlaylist(Long userId, PlayList playList);

    ApiResponse removeUserFromPlaylist(PlayList playList);

    Set<PlayList> getContributors();

    Set<UserEntity> getPlaylistUsers(PlayList playList);

    ApiResponse addUserToPlaylist(Long userId, Long playlistId);

    ApiResponse removeUserFromPlaylist(Long playlistId);

    Set<UserEntity> getPlaylistUsers(Long playlistId);

    void addAuthorToPlaylist(PlayList playList);
}
