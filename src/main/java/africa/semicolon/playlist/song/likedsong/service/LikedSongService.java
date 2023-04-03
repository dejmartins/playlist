package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import org.springframework.data.domain.Page;

public interface LikedSongService {

   ApiResponse likeSong(Long songId);
   SongResponse findALikedSong(Long songId);
   Page<SongResponse> findAllLikedSongsByUser(int pageNumber);
   ApiResponse dislikeSong(Long songId);
}
