package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.song.likedsong.dto.response.LikeSongResponse;
import africa.semicolon.playlist.song.likedsong.model.LikedSong;
import africa.semicolon.playlist.song.likedsong.repository.LikedSongRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikedSongServiceImpl implements LikedSongService{

    private final LikedSongRepository likedSongRepository;
    private final SongService songService;
    private final UserEntityService userEntityService;


    @Override
    public ApiResponse likeSong(Long userEntityId, String songTitle) {
        Song song = songService.getSongBySongTitle(songTitle);
        UserEntity userEntity = userEntityService.getUserById(userEntityId);

        LikedSong likedSong = LikedSong.builder()
                .userEntity(userEntity)
                .song(song)
                .liked(true)
                .dislike(false)
                .build();

        LikedSong savedLikedSong = likedSongRepository.save(likedSong);
        return ApiResponse.builder()
                .message("Song liked successfully")
                .build();
    }
}
