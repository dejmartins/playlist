package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ContributorService {

    ApiResponse addContributorToPlaylist(String username, PlayList playlist);

    ApiResponse removeContributor(String username, PlayList playlist);

    PageDto<PlayList> getPlaylistForUser(Pageable pageable);

    PageDto<UserEntity> getPlaylistContributors(PlayList playlist, Pageable pageable);

    ApiResponse addContributorToPlaylist(String username, Long playlistId);

    ApiResponse removeContributor(String userName, Long playlistId);

    PageDto<UserEntity> getPlaylistContributors(Long playlistId, Pageable pageable);

    void addAuthorToPlaylist(UserEntity userEntity, PlayList playlist);

    boolean isAuthor(UserEntity userEntity, PlayList playlist);

    boolean isAuthor(UserEntity userEntity, Long playlistId);

    boolean isAuthor(PlayList playlist);

    boolean isAuthor(Long playlistId);

    Set<UserEntity> getPlaylistContributors(Long playlistId);

    Set<UserEntity> getPlaylistContributors(PlayList playlist);
}
