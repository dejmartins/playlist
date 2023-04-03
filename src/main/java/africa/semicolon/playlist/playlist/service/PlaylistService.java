package africa.semicolon.playlist.playlist.service;

import africa.semicolon.playlist.config.ApiResponse;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import africa.semicolon.playlist.playlist.dto.UpdatePlaylistDetailsRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {

    CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistRequest);

    ApiResponse updatePlaylistImage(MultipartFile profileImage, Long playlist);

    FindPlaylistResponse findPlaylistBySlug(String slug);

    FindPlaylistResponse findPlaylistById(Long playlistId);

    ApiResponse deletePlaylistBySlug(String slug);

    ApiResponse deletePlaylistById(Long playlistId);

    FindPlaylistResponse updatePlaylistDetails(UpdatePlaylistDetailsRequest updatePlaylistDetailsRequest);

    PlayList privateFindPlaylistById(Long playlistId);

    PlayList privateFindPlaylistBySlug(String playlistSlug);

}
