package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;

public interface SongService {
    Song getSongBySongTitle(String songTitle);
    SongResponse searchSong(String songTitle);
    Song getSongById(Long songId);
}