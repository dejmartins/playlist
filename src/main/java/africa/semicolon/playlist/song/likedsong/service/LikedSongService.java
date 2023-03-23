package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.song.dto.LikeSongResponse;

public interface LikedSongService{

    LikeSongResponse likeSong(Long songId, Long userId);
}
