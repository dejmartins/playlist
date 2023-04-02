package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.response.UserDto;

import java.util.Set;

public interface ContributorService {

    ApiResponse addContributorToPlaylist(String username, PlayList playlist);

    ApiResponse removeContributor(String username, PlayList playlist);

    Set<PlayList> getPlaylistForUser();

    Set<UserEntity> getPlaylistContributors(PlayList playlist);

    ApiResponse addContributorToPlaylist(String username, Long playlistId);

    ApiResponse removeContributor(String userName, Long playlistId);

    Set<UserDto> getPlaylistContributors(Long playlistId);

    void addAuthorToPlaylist(UserEntity userEntity, PlayList playlist);

    boolean isAuthor(UserEntity userEntity, PlayList playlist);

    boolean isAuthor(UserEntity userEntity, Long playlistId);

    boolean isAuthor(PlayList playlist);

    boolean isAuthor(Long playlistId);
}
