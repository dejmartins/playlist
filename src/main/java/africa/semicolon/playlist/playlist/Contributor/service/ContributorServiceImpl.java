package africa.semicolon.playlist.playlist.Contributor.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.exception.ContributorNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.Contributor.demoContributor.Contributor;
import africa.semicolon.playlist.playlist.Contributor.repository.ContributorRepository;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository contributorRepository;
    private final AuthService authService;
    private final UserEntityService userEntityService;
    private final ModelMapper mapper;


    @Override
    public ApiResponse addContributorToPlaylist(String username, PlayList playlist) {
        if (!isAuthor(authService.getCurrentUser(), playlist)) throw new UnauthorizedActionException("Only the author can add new contributors");
        if (checkIfContributorExistsAlready(username, playlist)) throw new UnauthorizedActionException("Contributor already exists!");
        UserEntity userEntity = userEntityService.privateFindUserByUsername(username);
        Contributor newContributor = Contributor.builder()
                .user(userEntity)
                .playList(playlist)
                .isAuthor(false)
                .build();
        contributorRepository.save(newContributor);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
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
    public PageDto<PlayList> getPlaylistForUser(Pageable pageable) {
        UserEntity userEntity = authService.getCurrentUser();
        List<Contributor> foundContributor = contributorRepository.findAllByUser(userEntity);
        List<PlayList> playlists = foundContributor.stream().map(Contributor::getPlayList).toList();
        Page<PlayList> pending = new PageImpl<>(playlists, pageable, playlists.size());
        Type pageDtoTypeToken = new TypeToken<PageDto<PlayList>>() {
        }.getType();
        return mapper.map(pending, pageDtoTypeToken);
    }

    @Override
    public PageDto<UserEntity> getPlaylistContributors(PlayList playlist, Pageable pageable) {
        List<Contributor> foundContributor = contributorRepository.findAllByPlayList(playlist);
        List<UserEntity> users = foundContributor.stream().map(Contributor::getUser).toList();
        Page<UserEntity> pending = new PageImpl<>(users, pageable, users.size());
        Type pageDtoTypeToken = new TypeToken<PageDto<UserEntity>>() {
        }.getType();
        return mapper.map(pending, pageDtoTypeToken);
    }

    @Override
    public ApiResponse addContributorToPlaylist(String username, Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return addContributorToPlaylist(username, foundPlaylist);
    }

    @Override
    public ApiResponse removeContributor(String userName, Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return removeContributor(userName, foundPlaylist);
    }

    @Override
    public PageDto<UserEntity> getPlaylistContributors(Long playlistId, Pageable pageable) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return getPlaylistContributors(foundPlaylist, pageable);
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

    private boolean checkIfContributorExistsAlready(String username, PlayList playlist) {
        List<Contributor> allContributors = contributorRepository.findAllByPlayList(playlist);
        for (Contributor contributor : allContributors) {
            if (contributor.getUser().getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public Set<UserEntity> getPlaylistContributors(Long playlistId) {
        PlayList foundPlaylist = PlayList.builder().id(playlistId).build();
        return getPlaylistContributors(foundPlaylist);
    }

    @Override
    public Set<UserEntity> getPlaylistContributors(PlayList playlist) {
        if (!isAuthor(authService.getCurrentUser(), playlist)) throw new UnauthorizedActionException("Only the author can remove contributors");
        List<Contributor> foundContributor = contributorRepository.findAllByPlayList(playlist);
        Set<UserEntity> users = new HashSet<>();
        for (Contributor contributor : foundContributor) {
            users.add(contributor.getUser());
        }
        return users;
    }

}
