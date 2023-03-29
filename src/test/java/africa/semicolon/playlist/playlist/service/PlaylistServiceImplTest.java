package africa.semicolon.playlist.playlist.service;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
class PlaylistServiceImplTest {

    @Autowired
    private PlaylistService playlistService;

    @Test
    void testToCreatePlaylist() {
        CreatePlaylistReq request = CreatePlaylistReq.builder()
                .name("PLaylist Test 1")
                .slug("Bandana")
                .isPublic(true)
                .description("Contains Asake's songs and vibes")
                .build();

        CreatePlaylistResponse createdPlayList = playlistService.createPlaylist(request);
        assertNotNull(createdPlayList);
        assertNotNull(createdPlayList.getId());
        assertEquals(createdPlayList.getName(), request.getName());
        assertEquals(createdPlayList.getDescription(), request.getDescription());
        assertEquals(createdPlayList.getSlug(), request.getSlug());
    }

    @Test
    void testToFindPlaylistBySlug() {
        CreatePlaylistReq request = CreatePlaylistReq.builder()
                .name("PLaylist Test 2")
                .slug("Burnas")
                .isPublic(true)
                .description("Contains Odogwu's songs")
                .build();

        CreatePlaylistResponse createdPlayList = playlistService.createPlaylist(request);

        FindPlaylistResponse foundPlaylist = playlistService.findPlaylistBySlug("Burnas");
        assertNotNull(foundPlaylist);
        assertNotNull(foundPlaylist.getId());
        assertEquals(createdPlayList.getName(), foundPlaylist.getName());
        assertEquals(createdPlayList.getDescription(), foundPlaylist.getDescription());
        assertEquals(createdPlayList.getSlug(), foundPlaylist.getSlug());
        assertEquals(createdPlayList.getCoverImage(), foundPlaylist.getCoverImage());
        assertTrue(createdPlayList.getIsPublic());
    }


    @Test
    void testToFindPlaylistById() {
        CreatePlaylistReq request = CreatePlaylistReq.builder()
                .name("PLaylist Test 3")
                .slug("Jiggy")
                .isPublic(true)
                .description("Contains Jiggy's songs")
                .build();

        CreatePlaylistResponse createdPlayList = playlistService.createPlaylist(request);

        FindPlaylistResponse foundPlaylist = playlistService.findPlaylistById(createdPlayList.getId());
        assertNotNull(foundPlaylist);
        assertNotNull(foundPlaylist.getId());
        assertEquals(createdPlayList.getName(), foundPlaylist.getName());
        assertEquals(createdPlayList.getDescription(), foundPlaylist.getDescription());
        assertEquals(createdPlayList.getSlug(), foundPlaylist.getSlug());
        assertEquals(createdPlayList.getCoverImage(), foundPlaylist.getCoverImage());
        assertTrue(createdPlayList.getIsPublic());
    }
}