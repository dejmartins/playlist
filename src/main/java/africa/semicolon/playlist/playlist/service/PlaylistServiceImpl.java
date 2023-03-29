package africa.semicolon.playlist.playlist.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.config.cloud.CloudService;
import africa.semicolon.playlist.exception.PlaylistNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import africa.semicolon.playlist.playlist.dto.UpdatePlaylistDetailsRequest;
import africa.semicolon.playlist.playlist.repository.PlaylistRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;


@Service
@Slf4j
@AllArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final CloudService cloudService;
    private final PlaylistRepository playlistRepository;
    private final AuthService authService;
    private final ContributorService contributorService;

    @Override
    public CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistRequest) {
        PlayList playlistRequest = PlayList.builder()
                .slug(createPlaylistRequest.getSlug())
                .name(createPlaylistRequest.getName())
                .description(createPlaylistRequest.getDescription())
                .isPublic(createPlaylistRequest.getIsPublic())
                .build();
        PlayList savedPlaylist = playlistRepository.save(playlistRequest);
        contributorService.addAuthorToPlaylist(authService.getCurrentUser(), savedPlaylist);
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
    public ApiResponse updatePlaylistImage(MultipartFile profileImage, Long playlistId) {
        PlayList foundPlaylist = privateFindPlaylistById(playlistId);
        Set<UserEntity> contributors = contributorService.getPlaylistContributors(foundPlaylist);
        if (!contributors.contains(authService.getCurrentUser())) throw new UnauthorizedActionException();
        String imageUrl = cloudService.upload(profileImage);
        updatePlaylistProfileImage(imageUrl, foundPlaylist);

        return ApiResponse
                .builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public FindPlaylistResponse findPlaylistBySlug(String slug) {
        PlayList foundPlaylist = playlistRepository.findBySlug(slug).orElseThrow(PlaylistNotFoundException::new);
        return FindPlaylistResponse.builder()
                .id(foundPlaylist.getId())
                .name(foundPlaylist.getName())
                .coverImage(foundPlaylist.getCoverImage())
                .description(foundPlaylist.getDescription())
                .slug(foundPlaylist.getSlug())
                .isPublic(foundPlaylist.getIsPublic())
                .build();
    }

    @Override
    public FindPlaylistResponse findPlaylistById(Long playlistId) {
        PlayList foundPlaylist = playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
        return FindPlaylistResponse.builder()
                .id(foundPlaylist.getId())
                .name(foundPlaylist.getName())
                .coverImage(foundPlaylist.getCoverImage())
                .description(foundPlaylist.getDescription())
                .slug(foundPlaylist.getSlug())
                .isPublic(foundPlaylist.getIsPublic())
                .build();
    }

    @Override
    public ApiResponse deletePlaylistBySlug(String slug) {
        PlayList foundPlaylist = playlistRepository.findBySlug(slug).orElseThrow(PlaylistNotFoundException::new);
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.isAuthor(currentUser, foundPlaylist)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        playlistRepository.delete(foundPlaylist);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse deletePlaylistById(Long playlistId) {
        PlayList foundPlaylist = playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.isAuthor(currentUser, foundPlaylist)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        playlistRepository.delete(foundPlaylist);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public FindPlaylistResponse updatePlaylistDetails(UpdatePlaylistDetailsRequest updatePlaylistDetailsRequest) {
        PlayList foundPlaylist = playlistRepository.findById(updatePlaylistDetailsRequest.getPlaylistId()).orElseThrow(PlaylistNotFoundException::new);
        Set<UserEntity> contributors = contributorService.getPlaylistContributors(foundPlaylist);
        if (!contributors.contains(authService.getCurrentUser())) throw new UnauthorizedActionException();
        foundPlaylist.setDescription(updatePlaylistDetailsRequest.getDescription());
        foundPlaylist.setName(updatePlaylistDetailsRequest.getName());
        foundPlaylist.setSlug(updatePlaylistDetailsRequest.getSlug());
        foundPlaylist.setIsPublic(updatePlaylistDetailsRequest.getIsPublic());
        PlayList newUpdatedPlaylist = playlistRepository.save(foundPlaylist);
        return FindPlaylistResponse.builder()
                .id(newUpdatedPlaylist.getId())
                .name(newUpdatedPlaylist.getName())
                .coverImage(newUpdatedPlaylist.getCoverImage())
                .description(newUpdatedPlaylist.getDescription())
                .slug(newUpdatedPlaylist.getSlug())
                .isPublic(newUpdatedPlaylist.getIsPublic())
                .build();
    }

    @Override
    public PlayList privateFindPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
    }

    private void updatePlaylistProfileImage(String url, PlayList playList) {
        playList.setCoverImage(url);
        playlistRepository.save(playList);
    }
}
