package africa.semicolon.playlist.playlist.service;


import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import africa.semicolon.playlist.playlist.dto.UploadPlaylistImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PlaylistService {

    CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistRequest);

    UploadPlaylistImageResponse uploadProfileImage(MultipartFile profileImage, Long playlistId);

    FindPlaylistResponse findPlaylistBySlug(String slug);

    FindPlaylistResponse findPlaylistById(Long playlistId);

}
