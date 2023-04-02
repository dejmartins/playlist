package africa.semicolon.playlist.song.playlistSong.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.PlaylistNotFoundException;
import africa.semicolon.playlist.exception.PlaylistSongNotFoundException;
import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.song.playlistSong.demo.PlaylistSongEntity;
import africa.semicolon.playlist.song.playlistSong.repository.PlaylistSongRepository;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.user.data.models.UserEntity;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PlaylistSongServiceImpl implements PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;

    private final AuthService authService;

    private final ContributorService contributorService;

    private final PlaylistService playlistService;

    private final SongService songService;

    private final ModelMapper mapper;


    @Override
    public ApiResponse addSongToPlayList(PlayList playList, Song song) {
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        Song foundSong = songService.findSongById(song.getSongId());
        PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                .song(foundSong)
                .playList(playList)
                .build();
        playlistSongRepository.save(playlistSongEntity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse addSongsToPlayList(PlayList playList, List<Song> songs) {
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to add songs this playlist!");
        Set<PlaylistSongEntity> playlistSongEntitySet = new HashSet<>();
        int numberOfUnsavedSongs = 0;
        for (Song aSong: songs) {
            Song foundSong;
            try {
                foundSong = songService.findSongById(aSong.getSongId());
            }
            catch (SongNotFoundException songNotFoundException) {
                numberOfUnsavedSongs++;
                continue;
            }
            if (foundSong == null) continue;
            PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                    .playList(playList)
                    .song(foundSong)
                    .build();
            playlistSongEntitySet.add(playlistSongEntity);
        }

        int numberOfSavedSongs = songs.size() - numberOfUnsavedSongs;
        playlistSongRepository.saveAll(playlistSongEntitySet);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL : " + numberOfSavedSongs + "\nUNSUCCESSFUL: " + numberOfUnsavedSongs)
                .build();
    }

    @Override
    public ApiResponse deleteSongFromPlayList(PlayList playList, Song song) {
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        Song foundSong = songService.findSongById(song.getSongId());
        PlaylistSongEntity playlistSongEntity = playlistSongRepository.findByPlayListAndSong(playList, foundSong).orElseThrow(PlaylistNotFoundException::new);
        playlistSongRepository.delete(playlistSongEntity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public PageDto<Song> getSongsInPlaylist(PlayList playList, Pageable pageable) {
        List<PlaylistSongEntity> playlistSongEntity = playlistSongRepository.findByPlayList(playList).orElseThrow(PlaylistSongNotFoundException::new);
        List<Song> songsInPlaylist = new ArrayList<>();
        for (PlaylistSongEntity aPlaylistSongEntity : playlistSongEntity) {
            songsInPlaylist.add(aPlaylistSongEntity.getSong());
        }

        Pageable defaultPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize());
        Page<Song> pending = new PageImpl<>(songsInPlaylist, defaultPageable, songsInPlaylist.size());
        Type pageDtoTypeToken = new TypeToken<PageDto<UserEntity>>() {
        }.getType();
        return mapper.map(pending, pageDtoTypeToken);
    }

    @Override
    public ApiResponse addSongToPlayList(Long playlistId, Long songId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        Song foundSong = songService.findSongById(songId);
        return addSongToPlayList(foundPlaylist, foundSong);
    }

    @Override
    public ApiResponse addSongsToPlayList(Long playlistId, List<Song> songs) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return addSongsToPlayList(foundPlaylist, songs);
    }

    @Override
    public ApiResponse deleteSongFromPlayList(Long playlistId, Long songId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        Song foundSong = songService.findSongById(songId);
        return deleteSongFromPlayList(foundPlaylist, foundSong);
    }

    @Override
    public PageDto<Song> getSongsInPlaylist(Long playlistId, Pageable pageable) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return getSongsInPlaylist(foundPlaylist, pageable);
    }

}
