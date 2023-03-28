package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.user.data.models.UserEntity;
import java.util.Set;

public interface ContributorService {

    ApiResponse addContributorToPlaylist(String username, PlayList playList);

    ApiResponse removeContributor(String username, PlayList playList);

    Set<PlayList> getPlaylistForUser();

    Set<UserEntity> getPlaylistContributors(PlayList playList);

    ApiResponse addContributorToPlaylist(String username, Long playlistId);

    ApiResponse removeContributor(String userName, Long playlistId);

    Set<UserEntity> getPlaylistContributors(Long playlistId);

    CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistReq);

    void addAuthorToPlaylist(UserEntity userEntity, PlayList playList);
}
