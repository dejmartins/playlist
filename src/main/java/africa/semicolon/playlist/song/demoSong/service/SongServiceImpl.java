package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.song.demoSong.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
}
