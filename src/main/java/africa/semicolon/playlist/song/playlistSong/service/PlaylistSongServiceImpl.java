package africa.semicolon.playlist.song.playlistSong.service;

import africa.semicolon.playlist.auth.services.AuthService;
import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.exception.PlaylistNotFoundException;
import africa.semicolon.playlist.exception.PlaylistSongNotFoundException;
import africa.semicolon.playlist.exception.UnauthorizedActionException;
import africa.semicolon.playlist.playlist.Contributor.service.ContributorService;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.service.PlaylistService;
import africa.semicolon.playlist.song.demoSong.service.SongService;
import africa.semicolon.playlist.song.playlistSong.demo.PlaylistSongEntity;
import africa.semicolon.playlist.song.playlistSong.repository.PlaylistSongRepository;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.user.data.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    @Override
    public ApiResponse addSongToPlayList(PlayList playList, Song song) {
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                .song(song)
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
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        Set<PlaylistSongEntity> playlistSongEntitySet = new HashSet<>();

        for (Song aSong: songs) {
            PlaylistSongEntity playlistSongEntity = PlaylistSongEntity.builder()
                    .playList(playList)
                    .song(aSong)
                    .build();
            playlistSongEntitySet.add(playlistSongEntity);
        }

        playlistSongRepository.saveAll(playlistSongEntitySet);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse deleteSongFromPlayList(PlayList playList, Song song) {
        UserEntity currentUser = authService.getCurrentUser();
        if (!contributorService.getPlaylistContributors(playList).contains(currentUser)) throw new UnauthorizedActionException("You are not permitted to delete this playlist!");
        PlaylistSongEntity playlistSongEntity = playlistSongRepository.findByPlayListAndSong(playList, song).orElseThrow(PlaylistNotFoundException::new);
        playlistSongRepository.delete(playlistSongEntity);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public Set<Song> getSongsInPlaylist(PlayList playList) {
        List<PlaylistSongEntity> playlistSongEntity = playlistSongRepository.findByPlayList(playList).orElseThrow(PlaylistSongNotFoundException::new);
        Set<Song> songsInPlaylist = new HashSet<>();
        for (PlaylistSongEntity aPlaylistSongEntity : playlistSongEntity) {
            songsInPlaylist.add(aPlaylistSongEntity.getSong());
        }
        return songsInPlaylist;
    }

    @Override
    public ApiResponse addSongToPlayList(Long playlistId, String songTitle) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        Song foundSong = songService.getSongBySongTitle(songTitle);
        return addSongToPlayList(foundPlaylist, foundSong);
    }

    @Override
    public ApiResponse addSongsToPlayList(Long playlistId, List<Song> songs) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return addSongsToPlayList(foundPlaylist, songs);
    }

    @Override
    public ApiResponse deleteSongFromPlayList(Long playlistId, String songTitle) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        Song foundSong = songService.getSongBySongTitle(songTitle);
        return deleteSongFromPlayList(foundPlaylist, foundSong);
    }

    @Override
    public Set<Song> getSongsInPlaylist(Long playlistId) {
        PlayList foundPlaylist = playlistService.privateFindPlaylistById(playlistId);
        return getSongsInPlaylist(foundPlaylist);
    }

}
