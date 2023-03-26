package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.song.likedsong.dto.response.LikeSongResponse;

public interface LikedSongService {

   ApiResponse likeSong(Long userEntityId, String songTitle);
}
