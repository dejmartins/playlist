package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.exception.SongNotFoundException;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.demoSong.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ModelMapper mapper;

    @Override
    public SongResponse getSongByTitle(String songTitle) {
        Song song = songRepository.findSongByTitle(songTitle)
                .orElseThrow(()-> new SongNotFoundException("Song could not be found"));

        return mapper.map(song, SongResponse.class);
    }

    @Override
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }
}
