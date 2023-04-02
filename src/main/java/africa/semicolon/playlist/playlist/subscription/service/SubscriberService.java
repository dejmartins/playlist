package africa.semicolon.playlist.playlist.subscription.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.domain.Pageable;

public interface SubscriberService {

    ApiResponse subscribeToPlaylist(PlayList playList);

    ApiResponse subscribeToPlaylist(Long playlistId);

    boolean isSubscribed(PlayList playlist);

    boolean isSubscribed(Long playlistId);

    ApiResponse unsubscribeFromPlaylist(String playlistSlug);

    ApiResponse unsubscribeFromPlaylist(PlayList playList);

    ApiResponse removeUserFromPlaylist(UserEntity userEntity, PlayList playList);

    ApiResponse removeUserFromPlaylist(String userName, Long playlistId);

    PageDto<UserEntity> getSubscribers(Long playlistId, Pageable pageable);

    PageDto<UserEntity> getSubscribers(PlayList playList, Pageable pageable);

    PageDto<PlayList> getSubscribedPlaylists(Pageable pageable);

}
