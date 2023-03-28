package africa.semicolon.playlist.song.demoSong.service;

import africa.semicolon.playlist.song.demoSong.dto.response.SongResponse;
import africa.semicolon.playlist.song.demoSong.model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest
class SongServiceImplTest {
    @Autowired
    private SongService songService;
    private Song song;
    @BeforeEach
    void setUp() {
        song = Song.builder()
                .title("Party no dey stop")
                .albumName("AG Baby")
                .artiste("Adekunle Gold")
                .releaseDate(new Date(2023, Calendar.MARCH, 19))
                .spotifyId("spotifyId-123")
                .build();
    }

    @Test
    void getSongFromOurDB() {
        songService.saveSong(song);
        SongResponse response = songService.getSongByTitle("Party no dey stop");

        assertThat(response).isNotNull();
        assertThat(response.getAlbumName()).isEqualTo("AG Baby");
    }
}