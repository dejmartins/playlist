package africa.semicolon.playlist.song.likedsong.service;


import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.song.likedsong.model.LikedSong;
import africa.semicolon.playlist.song.likedsong.repository.LikedSongRepository;
import africa.semicolon.playlist.user.data.models.UserEntity;
import africa.semicolon.playlist.user.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.h2.engine.User;
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
    private  final AuthService authService;
    private final ModelMapper mapper;



    @Override
    public ApiResponse likeSong(Long songId) {
        UserEntity user = authService.getCurrentUser();
        Song song = songService.getSongById(songId);

        LikedSong likedSong = LikedSong.builder()
                .userEntity(user)
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
    public SongResponse findALikedSong(Long songId) {
        UserEntity user = authService.getCurrentUser();

        LikedSong likedSong = getLikedSong(user.getId(), songId);
        Song song = likedSong.getSong();

        return mapper.map(song, SongResponse.class);
    }

    @Override
    public Page<SongResponse> findAllLikedSongsByUser(int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber -= 1;

        Pageable pageable = PageRequest.of(pageNumber, MAX_PAGE_NUMBER);

        UserEntity user = authService.getCurrentUser();

        List<LikedSong> likedSongs = likedSongRepository.findAllByUserEntity_Id(user.getId());

        List<SongResponse> songResponses = likedSongs.stream()
                .map(likedSong->mapper.map(likedSong.getSong(), SongResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(songResponses, pageable, likedSongs.size());
    }

    @Override
    public ApiResponse dislikeSong(Long songId) {
        UserEntity user = authService.getCurrentUser();

        LikedSong likedSong = getLikedSong(user.getId(), songId);
        likedSong.setLiked(false);
        likedSong.setDislike(true);  //I feel this is not necessary but just in case
        likedSongRepository.delete(likedSong);

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message(SONG_DISLIKED)
                .build();
    }
    private LikedSong getLikedSong(Long userEntityId, Long songId) {
        return likedSongRepository.findLikedSongByUserEntity_IdAndSong_SongId(userEntityId, songId)
                .orElseThrow(()->new SongNotFoundException(SONG_NOT_FOUND));
    }
}