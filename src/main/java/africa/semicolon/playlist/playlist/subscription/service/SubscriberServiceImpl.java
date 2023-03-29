package africa.semicolon.playlist.playlist.subscription.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.ContributorNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.playlist.subscription.demoSubscriber.Subscriber;
import africa.semicolon.playlist.playlist.subscription.repository.SubscriberRepository;
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
public class SubscriberServiceImpl implements SubscriberService {

    private final AuthService authService;
    private final PlaylistService playlistService;
    private final UserEntityService userEntityService;
    private final SubscriberRepository subscriberRepository;
    private final ContributorService contributorService;

    @Override
    public ApiResponse subscribeToPlaylist(PlayList playList) {
        UserEntity userEntity = authService.getCurrentUser();
        Subscriber subscriber = Subscriber.builder()
                .user(userEntity)
                .playList(playList)
                .build();
        subscriberRepository.save(subscriber);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse subscribeToPlaylist(Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return subscribeToPlaylist(foundPlaylist);
    }

    @Override
    public boolean isSubscribed(PlayList playlist) {
        UserEntity userEntity = authService.getCurrentUser();
        return getSubscribers(playlist).contains(userEntity);
    }

    @Override
    public boolean isSubscribed(Long playlistId) {
        UserEntity userEntity = authService.getCurrentUser();
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return getSubscribers(foundPlaylist).contains(userEntity);
    }

    @Override
    public ApiResponse unsubscribeFromPlaylist(Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return unsubscribeFromPlaylist(foundPlaylist);
    }

    @Override
    public ApiResponse unsubscribeFromPlaylist(PlayList playList) {
        UserEntity userEntity = authService.getCurrentUser();
        Subscriber subscriber = subscriberRepository.findByUserAndPlayList(userEntity, playList).orElseThrow();
        subscriberRepository.delete(subscriber);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse removeUserFromPlaylist(UserEntity userEntity, PlayList playList) {
        if (!contributorService.isAuthor(authService.getCurrentUser(), playList)) throw new UnauthorizedActionException("Only the author can add new contributors");
        Subscriber subscriber = subscriberRepository.findByUserAndPlayList(userEntity, playList).orElseThrow();
        subscriberRepository.delete(subscriber);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse removeUserFromPlaylist(String userName, Long playlistId) {
        UserEntity userEntity = userEntityService.privateFindUserByUsername(userName);
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return removeUserFromPlaylist(userEntity, foundPlaylist);
    }

    @Override
    public Set<UserEntity> getSubscribers(Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return getSubscribers(foundPlaylist);
    }

    @Override
    public Set<UserEntity> getSubscribers(PlayList playList) {
        if (!contributorService.isAuthor(authService.getCurrentUser(), playList)) throw new UnauthorizedActionException("Only the author can add new contributors");
        Set<UserEntity> userEntitySet = new HashSet<>();
        List<Subscriber> subscribers = subscriberRepository.findAllByPlayList(playList).orElseThrow(ContributorNotFoundException::new);
        for (Subscriber aSubscriber : subscribers) {
            userEntitySet.add(aSubscriber.getUser());
        }
        return userEntitySet;
    }

    @Override
    public Set<PlayList> getSubscribedPlaylists() {
        UserEntity userEntity = authService.getCurrentUser();
        Set<PlayList> playListSet = new HashSet<>();
        List<Subscriber> subscribers = subscriberRepository.findAllByUser(userEntity).orElseThrow();
        for (Subscriber aSubscriber : subscribers) {
            playListSet.add(aSubscriber.getPlayList());
        }
        return playListSet;
    }

}
