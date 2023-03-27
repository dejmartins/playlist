package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.song.likedsong.model.LikedSong;
import africa.semicolon.playlist.song.likedsong.repository.LikedSongRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static africa.semicolon.playlist.config.utilities.PlaylistUtilities.*;

@Service
@RequiredArgsConstructor
public class LikedSongServiceImpl implements LikedSongService {

    private final LikedSongRepository likedSongRepository;
    private final SongService songService;
    private final UserEntityService userEntityService;
    private final ModelMapper mapper;
    public final int MAX_PAGE_NUMBER = 5;


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

        likedSongRepository.save(likedSong);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(SONG_LIKED)
                .build();
    }

    @Override
    public SongResponse findALikedSong(Long userEntityId, String songTitle) {
        LikedSong likedSong = getLikedSong(userEntityId, songTitle);
        Song song = likedSong.getSong();
        return mapper.map(song, SongResponse.class);
    }

    @Override
    public Page<SongResponse> findAllLikedSongsByUser(Long userEntityId, String songTitle, int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber -= 1;

        Pageable pageable = PageRequest.of(pageNumber, MAX_PAGE_NUMBER);

        List<LikedSong> likedSongs = likedSongRepository
                .findLikedSongByUserEntity_IdAndSong_Title(userEntityId, songTitle);

        List<SongResponse> songResponses = likedSongs.stream()
                .map(likedSong->mapper.map(likedSong.getSong(), SongResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(songResponses, pageable, likedSongs.size());
    }

    @Override
    public ApiResponse dislikeSong(Long userEntityId, String songTitle) {
        LikedSong likedSong = getLikedSong(userEntityId, songTitle);
        likedSong.setLiked(false);
        likedSong.setDislike(true);  //I feel this is not necessary but just in case
        likedSongRepository.delete(likedSong);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(SONG_DISLIKED)
                .build();
    }
    private LikedSong getLikedSong(Long userEntityId, String songTitle) {
        return likedSongRepository.findLikedSongBySong_TitleAndUserEntity_Id(songTitle, userEntityId)
                .orElseThrow(()->new SongNotFoundException(SONG_NOT_FOUND));
    }
}