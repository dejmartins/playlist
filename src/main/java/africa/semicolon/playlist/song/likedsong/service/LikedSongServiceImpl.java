package africa.semicolon.playlist.song.likedsong.service;

import africa.semicolon.playlist.song.dto.LikeSongResponse;
import africa.semicolon.playlist.song.likedsong.repository.LikedSongRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikedSongServiceImpl implements LikedSongService{

    private final LikedSongRepository likedSongRepository;

    @Override
    public LikeSongResponse likeSong(Long songId, Long userId) {
        /*todo so i have issue with finding song now that we have not written method to find song
        *  we can fin*/
        return null;
    }
}
