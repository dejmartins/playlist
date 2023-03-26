package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;

public interface SongService {
    SongResponse getSongByTitle(String songTitle);
    void saveSong(Song song);
    Song getSongFromSpotify(String songTitle);
    SongResponse searchSong(String songTitle);

}
