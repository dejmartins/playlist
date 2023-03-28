package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.ContributorNotFoundException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.Contributor.demoContributor.Contributor;
import africa.semicolon.playlist.playlist.Contributor.repository.ContributorRepository;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.service.PlaylistService;
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
    private final PlaylistService playlistService;
    private final UserEntityService userEntityService;


    @Override
    public ApiResponse addContributorToPlaylist(String username, PlayList playList) {
        UserEntity userEntity = userEntityService.privateFindUserByUsername(username);
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
    public ApiResponse removeContributor(String username, PlayList playList) {
        UserEntity userEntity = userEntityService.privateFindUserByUsername(username);
        Contributor foundContributor = pluRepository.findByUserAndPlayList(userEntity, playList).orElseThrow(ContributorNotFoundException::new);
        pluRepository.delete(foundContributor);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Set<PlayList> getPlaylistForUser() {
        UserEntity userEntity = authService.getCurrentUser();
        List<Contributor> foundContributor = pluRepository.findAllByUser(userEntity).orElseThrow(ContributorNotFoundException::new);
        Set<PlayList> userPlaylists = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            userPlaylists.add(contributor.getPlayList());
        }
        return userPlaylists;
    }

    @Override
    public Set<UserEntity> getPlaylistContributors(PlayList playList) {
        List<Contributor> foundContributor = pluRepository.findAllByPlayList(playList).orElseThrow(ContributorNotFoundException::new);
        Set<UserEntity> users = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            users.add(contributor.getUser());
        }
        return users;
    }

    @Override
    public ApiResponse addContributorToPlaylist(String username, Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
//        PlayList playlist = PlayList.builder().id(playlistId).build();
        return addContributorToPlaylist(username, foundPlaylist);
    }

    @Override
    public ApiResponse removeContributor(String userName, Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
//        PlayList playlist = PlayList.builder().id(playlistId).build();
        return removeContributor(userName, foundPlaylist);
    }

    @Override
    public Set<UserEntity> getPlaylistContributors(Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
//        PlayList playlist = PlayList.builder().id(playlistId).build();
        return getPlaylistContributors(foundPlaylist);
    }

    @Override
    public CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistReq) {
        UserEntity userEntity = authService.getCurrentUser();
        PlayList savedPlaylist = playlistService.createPlaylist(createPlaylistReq);
        addAuthorToPlaylist(userEntity, savedPlaylist);
        return CreatePlaylistResponse.builder()
                .id(savedPlaylist.getId())
                .name(savedPlaylist.getName())
                .coverImage(savedPlaylist.getCoverImage())
                .description(savedPlaylist.getDescription())
                .slug(savedPlaylist.getSlug())
                .isPublic(savedPlaylist.getIsPublic())
                .build();
    }

    @Override
    public void addAuthorToPlaylist(UserEntity userEntity, PlayList playList) {
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playList)
                .isAuthor(true)
                .build();
        pluRepository.save(newContributor);
    }


}
