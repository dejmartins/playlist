package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.ContributorNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.dto.ContributorDto;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.Contributor.demoContributor.Contributor;
import africa.semicolon.playlist.playlist.Contributor.repository.ContributorRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.dto.response.UserDto;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository contributorRepository;
    private final AuthService authService;
    private final UserEntityService userEntityService;
    private final ModelMapper mapper;


    @Override
    public ContributorDto addContributorToPlaylist(String username, PlayList playlist) {
        if (!isAuthor(authService.getCurrentUser(), playlist)) throw new UnauthorizedActionException("Only the author can add new contributors");
        UserEntity userEntity = userEntityService.privateFindUserByUsername(username);
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playlist)
                .isAuthor(false)
                .build();
        Contributor savedContributor = contributorRepository.save(newContributor);
        return mapper.map(savedContributor, ContributorDto.class);
    }

    @Override
    public ApiResponse removeContributor(String username, PlayList playlist) {
        if (!isAuthor(authService.getCurrentUser(), playlist)) throw new UnauthorizedActionException("Only the author can remove contributors");
        UserEntity userEntity = userEntityService.privateFindUserByUsername(username);
        Contributor foundContributor = contributorRepository.findByUserAndPlayList(userEntity, playlist).orElseThrow(ContributorNotFoundException::new);
        contributorRepository.delete(foundContributor);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public Set<PlayList> getPlaylistForUser() {
        UserEntity userEntity = authService.getCurrentUser();
        List<Contributor> foundContributor = contributorRepository.findAllByUser(userEntity).orElseThrow(ContributorNotFoundException::new);
        Set<PlayList> userPlaylists = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            userPlaylists.add(contributor.getPlayList());
        }
        return userPlaylists;
    }

    @Override
    public Set<UserEntity> getPlaylistContributors(PlayList playlist) {
        if (!isAuthor(authService.getCurrentUser(), playlist)) throw new UnauthorizedActionException("Only the author can remove contributors");
        List<Contributor> foundContributor = contributorRepository.findAllByPlayList(playlist).orElseThrow(ContributorNotFoundException::new);
        Set<UserEntity> users = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            users.add(contributor.getUser());
        }
        return users;
    }

    @Override
    public ContributorDto addContributorToPlaylist(String username, Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        log.debug("FoundPlaylist -> {}", foundPlaylist);
        return addContributorToPlaylist(username, foundPlaylist);
    }

    @Override
    public ApiResponse removeContributor(String userName, Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return removeContributor(userName, foundPlaylist);
    }

    @Override
    public Set<ContributorDto> getPlaylistContributors(Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();

        Type userDtoTypeToken = new TypeToken<Set<ContributorDto>>() {
        }.getType();

        return mapper.map(getPlaylistContributors(foundPlaylist), userDtoTypeToken);
    }

    @Override
    public void addAuthorToPlaylist(UserEntity userEntity, PlayList playlist) {
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playlist)
                .isAuthor(true)
                .build();
        contributorRepository.save(newContributor);
    }

    @Override
    public boolean isAuthor(UserEntity userEntity, PlayList playlist) {
        Contributor foundContributor = contributorRepository.findByUserAndPlayList(userEntity, playlist).orElseThrow(ContributorNotFoundException::new);
        return foundContributor.isAuthor();
    }

    @Override
    public boolean isAuthor(UserEntity userEntity, Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return isAuthor(userEntity, foundPlaylist);
    }

    @Override
    public boolean isAuthor(PlayList playlist) {
        UserEntity userEntity = authService.getCurrentUser();
        return isAuthor(userEntity, playlist);
    }

    @Override
    public boolean isAuthor(Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return isAuthor(foundPlaylist);
    }

}
