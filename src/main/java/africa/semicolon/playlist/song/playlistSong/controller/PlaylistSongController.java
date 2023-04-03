package africa.semicolon.playlist.song.playlistSong.controller;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.dto.PageDto;
import africa.semicolon.playlist.song.demoSong.model.Song;
import africa.semicolon.playlist.song.playlistSong.service.PlaylistSongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/playlist-song")
public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    @Autowired
    public PlaylistSongController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    @Operation(summary = "Adds song to a playlist",
            description = "Returns a Response entity containing a message and HTTP status code")
    @PostMapping("/addSong/{playlistId}/{songId}")
    public ResponseEntity<ApiResponse> addSongToPlaylist(
            @PathVariable
            @Parameter(name = "songId", description = "The id of the song to add to the playlist",
                    required = true) @NotNull @Valid
            Long songId,
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the playlist to edit",
                    required = true) @NotNull @Valid
            Long playlistId) {
        ApiResponse response = playlistSongService.addSongToPlayList(playlistId, songId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Adds a list of songs to a playlist",
            description = "Returns a Response entity containing a message and HTTP status code")
    @PostMapping("/addSongs")
    public ResponseEntity<ApiResponse> addSongsToPlaylist(
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the playlist to edit",
                    required = true) @NotNull @Valid
            Long playlistId,
            @RequestBody
            @Parameter(name = "songs", description = "A list of songs to add to a playlist",
                    required = true) @NotNull
            List<Song> songs) {
        ApiResponse response = playlistSongService.addSongsToPlayList(playlistId, songs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a song from a playlist",
            description = "Returns a Response entity containing a message and HTTP status code")
    @DeleteMapping("/delete/{playlistId}/{songId}")
    public ResponseEntity<ApiResponse> deleteSongFromPlaylist(
            @PathVariable
            @Parameter(name = "songId", description = "The title of the song to delete from the playlist",
                    required = true) @Valid @NotNull
            Long songId,
            @PathVariable
            @Parameter(name = "playlistId", description = "The id of the playlist to edit",
                    required = true) @NotNull @Valid
            Long playlistId) {
        ApiResponse response = playlistSongService.deleteSongFromPlayList(playlistId, songId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Gets all songs in a playlist",
            description = "Returns a Response entity containing all songs in a playlist and HTTP status code")
    @GetMapping("/songs")
    public ResponseEntity<PageDto<Song>> getSongsInPlaylist(
            @RequestParam
            @Parameter(name = "playlistId", description = "The id of the playlist whose songs are required", example = "1L",
                    required = true) @NotNull @Valid
            Long playlistId, @ParameterObject Pageable pageable) {
        PageDto<Song> response = playlistSongService.getSongsInPlaylist(playlistId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
