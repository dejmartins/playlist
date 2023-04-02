package africa.semicolon.playlist.playlist.subscription.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.ContributorNotFoundException;
import africa.semicolon.playlist.exception.SubscriptionNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.playlist.subscription.demoSubscriber.Subscriber;
import africa.semicolon.playlist.playlist.subscription.repository.SubscriberRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final AuthService authService;
    private final PlaylistService playlistService;
    private final UserEntityService userEntityService;
    private final SubscriberRepository subscriberRepository;
    private final ContributorService contributorService;
    private final ModelMapper mapper;

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

    private Set<UserEntity> getSubscribers(PlayList playList) {
        if (!contributorService.isAuthor(authService.getCurrentUser(), playList)) throw new UnauthorizedActionException("Only the author can add new contributors");
        Set<UserEntity> userEntitySet = new HashSet<>();
        List<Subscriber> subscribers = subscriberRepository.findAllByPlayList(playList).orElseThrow(ContributorNotFoundException::new);
        for (Subscriber aSubscriber : subscribers) {
            userEntitySet.add(aSubscriber.getUser());
        }
        return userEntitySet;
    }

    @Override
    public ApiResponse unsubscribeFromPlaylist(String playlistSlug) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistBySlug(playlistSlug);
        return unsubscribeFromPlaylist(foundPlaylist);
    }

    @Override
    public ApiResponse unsubscribeFromPlaylist(PlayList playList) {
        UserEntity userEntity = authService.getCurrentUser();
        Subscriber subscriber = subscriberRepository.findByUserAndPlayList(userEntity, playList).orElseThrow(SubscriptionNotFoundException::new);
        subscriberRepository.delete(subscriber);
        return ApiResponse.builder()
                .message("SUCCESSFUL")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ApiResponse removeUserFromPlaylist(UserEntity userEntity, PlayList playList) {
        if (!contributorService.isAuthor(authService.getCurrentUser(), playList)) throw new UnauthorizedActionException("Only the author can add new contributors");
        Subscriber subscriber = subscriberRepository.findByUserAndPlayList(userEntity, playList).orElseThrow(SubscriptionNotFoundException::new);
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
    public PageDto<UserEntity> getSubscribers(Long playlistId, Pageable pageable) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return getSubscribers(foundPlaylist, pageable);
    }

    @Override
    public PageDto<UserEntity> getSubscribers(PlayList playList, Pageable pageable) {
        if (!contributorService.isAuthor(authService.getCurrentUser(), playList)) throw new UnauthorizedActionException("Only the author can add new contributors");
        List<UserEntity> userEntityList = new ArrayList<>();
        List<Subscriber> subscribers = subscriberRepository.findAllByPlayList(playList).orElseThrow(ContributorNotFoundException::new);
        for (Subscriber aSubscriber : subscribers) {
            userEntityList.add(aSubscriber.getUser());
        }
        Pageable defaultPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize());
        Page<UserEntity> pending = new PageImpl<>(userEntityList, defaultPageable, userEntityList.size());
        Type pageDtoTypeToken = new TypeToken<PageDto<UserEntity>>() {
        }.getType();
        return mapper.map(pending, pageDtoTypeToken);
    }

    @Override
    public PageDto<PlayList> getSubscribedPlaylists(Pageable pageable) {
        UserEntity userEntity = authService.getCurrentUser();
        List<PlayList> playListSet = new ArrayList<>();
        List<Subscriber> subscribers = subscriberRepository.findAllByUser(userEntity).orElseThrow(SubscriptionNotFoundException::new);
        for (Subscriber aSubscriber : subscribers) {
            playListSet.add(aSubscriber.getPlayList());
        }
        Pageable defaultPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize());
        Page<PlayList> pending = new PageImpl<>(playListSet, defaultPageable, playListSet.size());
        Type pageDtoTypeToken = new TypeToken<PageDto<UserEntity>>() {
        }.getType();
        return mapper.map(pending, pageDtoTypeToken);
    }

}
