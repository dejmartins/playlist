package africa.semicolon.playlist.playlist.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {

    CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistRequest);

    ApiResponse uploadProfileImage(MultipartFile profileImage, Long playlist);

    FindPlaylistResponse findPlaylistBySlug(String slug);

    FindPlaylistResponse findPlaylistById(Long playlistId);

    ApiResponse deletePlaylistBySlug(String slug);

    ApiResponse deletePlaylistById(Long playlistId);

    FindPlaylistResponse updatePlaylistDetails(Long playlistId, JsonPatch jsonPatch);

}
