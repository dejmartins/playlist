package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.config.spotify.SpotifyService;
import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.repository.SongRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final SpotifyService spotifyService;
    private final ModelMapper mapper;

    @Override
    public SongResponse getSongByTitle(String songTitle) {
        Song song = songRepository.findSongByTitle(songTitle)
                .orElseThrow(()-> new SongNotFoundException("Song could not be found"));

        return mapper.map(song, SongResponse.class);
    }

    @Override
    public void saveSong(Song song) {
        songRepository.save(song);
    }
    @Override
    public SongResponse searchSong(String songTitle) {
        SongResponse response;
        response = getSongByTitle(songTitle);
        if (response != null) {
           return response;
        } else {
             Song song = getSongFromSpotify(songTitle);
             if (song == null) {
                 throw new SongNotFoundException("Song could not be found");
             }
            Song savedSong = songRepository.save(song);
            return mapper.map(savedSong, SongResponse.class);
        }
    }
    private Song getSongFromSpotify(String songTitle) {
        try {
            return spotifyService.findingSong(songTitle);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
