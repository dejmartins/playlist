package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import org.springframework.data.domain.Page;

public interface LikedSongService {

   ApiResponse likeSong(Long userEntityId, String songTitle);
   SongResponse findALikedSong(Long userEntityId, String songTitle);
   Page<SongResponse> findAllLikedSongsByUser(Long userEntityId, String songTitle, int pageNumber);
}
