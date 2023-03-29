package africa.semicolon.playlist.config.spotify;

import africa.semicolon.playlist.song.demoSong.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
class SpotifyServiceTest {

    @Autowired
    private SpotifyService spotifyService;

    @Test
    void findSongTest() {
        Date releaseDate;
        try {
            releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-15");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Song foundSong = Song.builder()
                .spotifyId("6wuMo4ZR83PhlhXhJ1S3VY")
                .playBack("https://p.scdn.co/mp3-preview/a1a274b5cf0f9b9aa40382aa43cc7d6300be31b2?cid=144b644df0bf44e4af679d1638fbe7f7")
                .title("GWAGWALADA")
                .artiste("BNXN fka Buju, Kizz Daniel, Seyi Vibez, ")
                .image("https://i.scdn.co/image/ab67616d00001e020a4ec262a9a596070c6f70e7")
                .explicit(false)
                .releaseDate(releaseDate)
                .albumName("GWAGWALADA")
                .duration("188985")
                .externalHref("https://open.spotify.com/track/6wuMo4ZR83PhlhXhJ1S3VY")
                .build();

        Song spotifySong = spotifyService.findingSong("GWAGWALADA");
        assertEquals(foundSong.getSpotifyId(), spotifySong.getSpotifyId());
        assertEquals(foundSong.getPlayBack(), spotifySong.getPlayBack());
        assertEquals(foundSong.getTitle(), spotifySong.getTitle());
        assertEquals(foundSong.getArtiste(), spotifySong.getArtiste());
        assertEquals(foundSong.getImage(), spotifySong.getImage());
        assertEquals(foundSong.getReleaseDate(), spotifySong.getReleaseDate());
        assertEquals(foundSong.getAlbumName(), spotifySong.getAlbumName());
        assertEquals(foundSong.getDuration(), spotifySong.getDuration());
        assertEquals(foundSong.getExternalHref(), spotifySong.getExternalHref());
    }

}
