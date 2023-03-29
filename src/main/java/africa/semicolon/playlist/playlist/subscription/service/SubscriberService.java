package africa.semicolon.playlist.playlist.subscription.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.user.data.models.UserEntity;
import java.util.Set;

public interface SubscriberService {

    ApiResponse subscribeToPlaylist(PlayList playList);

    ApiResponse subscribeToPlaylist(Long playlistId);

    boolean isSubscribed(PlayList playlist);

    boolean isSubscribed(Long playlistId);

    ApiResponse unsubscribeFromPlaylist(Long playlistId);

    ApiResponse unsubscribeFromPlaylist(PlayList playList);

    ApiResponse removeUserFromPlaylist(UserEntity userEntity, PlayList playList);

    ApiResponse removeUserFromPlaylist(String userName, Long playlistId);

    Set<UserEntity> getSubscribers(Long playlistId);

    Set<UserEntity> getSubscribers(PlayList playList);

    Set<PlayList> getSubscribedPlaylists();

}
