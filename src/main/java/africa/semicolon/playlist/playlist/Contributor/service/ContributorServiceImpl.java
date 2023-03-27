package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.PlaylistUserNotFoundException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.Contributor.demoContributor.Contributor;
import africa.semicolon.playlist.playlist.Contributor.repository.ContributorRepository;
//import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository pluRepository;
    private final AuthService authService;
//    private final PlaylistService playlistService;
    private final UserEntityService userEntityService;


    @Override
    public ApiResponse addUserToPlaylist(Long userId, PlayList playList) {
        UserEntity userEntity = userEntityService.privateFindUserById(userId);
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playList)
                .isAuthor(false)
                .build();
        pluRepository.save(newContributor);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse removeUserFromPlaylist(PlayList playList) {
        UserEntity userEntity = authService.getCurrentUser();
        Contributor foundContributor = pluRepository.findByUserAndPlayList(userEntity, playList).orElseThrow(PlaylistUserNotFoundException::new);
        pluRepository.delete(foundContributor);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Set<PlayList> getContributors() {
        UserEntity userEntity = authService.getCurrentUser();
        List<Contributor> foundContributor = pluRepository.findByUser(userEntity).orElseThrow(PlaylistUserNotFoundException::new);
        Set<PlayList> userPlaylists = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            userPlaylists.add(contributor.getPlayList());
        }
        return userPlaylists;
    }

    @Override
    public Set<UserEntity> getPlaylistUsers(PlayList playList) {
        List<Contributor> foundContributor = pluRepository.findByPlayList(playList).orElseThrow(PlaylistUserNotFoundException::new);
        Set<UserEntity> users = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            users.add(contributor.getUser());
        }
        return users;
    }

    @Override
    public ApiResponse addUserToPlaylist(Long userId, Long playlistId) {
//        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        PlayList playlist = PlayList.builder().id(playlistId).build();
        return addUserToPlaylist(userId, playlist);
    }

    @Override
    public ApiResponse removeUserFromPlaylist(Long playlistId) {
//        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        PlayList playlist = PlayList.builder().id(playlistId).build();
        return removeUserFromPlaylist(playlist);
    }

    @Override
    public Set<UserEntity> getPlaylistUsers(Long playlistId) {
//        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        PlayList playlist = PlayList.builder().id(playlistId).build();
        return getPlaylistUsers(playlist);
    }

    @Override
    public void addAuthorToPlaylist(PlayList playList) {
        UserEntity userEntity = authService.getCurrentUser();
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playList)
                .isAuthor(true)
                .build();
        pluRepository.save(newContributor);
    }


}
