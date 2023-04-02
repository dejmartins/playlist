package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.config.spotify.SpotifyService;
import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static africa.semicolon.playlist.config.utilities.PlaylistUtilities.SONG_NOT_FOUND;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SpotifyService spotifyService;
    private final ModelMapper mapper;

    @Override
    public SongResponse getSongByTitle(String songTitle) {
        Song song = songRepository.findSongByTitle(songTitle)
                .orElseThrow(()->new SongNotFoundException(SONG_NOT_FOUND));

        return mapper.map(song, SongResponse.class);
    }

    @Override
    public Song getSongBySongTitle(String songTitle) {
        return songRepository.findSongByTitle(songTitle)
                .orElseThrow(()-> new SongNotFoundException(SONG_NOT_FOUND));
    }

    @Override
    public void saveSong(Song song) {
        songRepository.save(song);
    }

    @Override
    public SongResponse searchSong(String songTitle) {
        SongResponse response = getSongByTitle(songTitle);
        if (response != null) {
            return response;
        } else {
            Song song = getSongFromSpotify(songTitle);
            if (song == null) {
                throw new SongNotFoundException(SONG_NOT_FOUND);
            }
            Song savedSong = songRepository.save(song);
            return mapper.map(savedSong, SongResponse.class);
        }
    }

    @Override
    public Song findSongById(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException(SONG_NOT_FOUND));
    }

    private Song getSongFromSpotify(String songTitle) {
        return spotifyService.findingSong(songTitle);
    }
}
