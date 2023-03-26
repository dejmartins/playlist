package africa.semicolon.playlist.playlist.service;

import africa.semicolon.playlist.ApiResponse;
import africa.semicolon.playlist.config.cloud.CloudService;
import africa.semicolon.playlist.exception.PlaylistNotFoundException;
import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistReq;
import africa.semicolon.playlist.playlist.dto.CreatePlaylistResponse;
import africa.semicolon.playlist.playlist.dto.FindPlaylistResponse;
import africa.semicolon.playlist.playlist.repository.PlaylistRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final CloudService cloudService;
    private final PlaylistRepository playlistRepository;

    @Override
    public CreatePlaylistResponse createPlaylist(CreatePlaylistReq createPlaylistRequest) {
        PlayList playlistRequest = PlayList.builder()
                .slug(createPlaylistRequest.getSlug())
                .name(createPlaylistRequest.getName())
                .description(createPlaylistRequest.getDescription())
                .isPublic(createPlaylistRequest.getIsPublic())
                .build();

        PlayList savedPlaylist = playlistRepository.save(playlistRequest);
        return CreatePlaylistResponse.builder()
                .id(savedPlaylist.getId())
                .name(savedPlaylist.getName())
                .coverImage(savedPlaylist.getCoverImage())
                .description(savedPlaylist.getDescription())
                .slug(savedPlaylist.getSlug())
                .isPublic(savedPlaylist.getIsPublic())
                .build();
    }

    @Override
    public ApiResponse updatePlaylistImage(MultipartFile profileImage, Long playlistId) {
        Optional<PlayList> foundPlaylist = privateFindPlaylistById(playlistId);
        if (foundPlaylist.isEmpty()) throw new PlaylistNotFoundException("Playlist with id " + playlistId + " not found");
        String imageUrl = cloudService.upload(profileImage);
        foundPlaylist.ifPresent(playList -> updatePlaylistProfileImage(imageUrl, playList));

        return ApiResponse
                .builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .build();
    }

    @Override
    public FindPlaylistResponse findPlaylistBySlug(String slug) {
        PlayList foundPlaylist = playlistRepository.findBySlug(slug).orElseThrow(PlaylistNotFoundException::new);
        return FindPlaylistResponse.builder()
                .id(foundPlaylist.getId())
                .name(foundPlaylist.getName())
                .coverImage(foundPlaylist.getCoverImage())
                .description(foundPlaylist.getDescription())
                .slug(foundPlaylist.getSlug())
                .isPublic(foundPlaylist.getIsPublic())
                .build();
    }

    @Override
    public FindPlaylistResponse findPlaylistById(Long playlistId) {
        PlayList foundPlaylist = playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
        return FindPlaylistResponse.builder()
                .id(foundPlaylist.getId())
                .name(foundPlaylist.getName())
                .coverImage(foundPlaylist.getCoverImage())
                .description(foundPlaylist.getDescription())
                .slug(foundPlaylist.getSlug())
                .isPublic(foundPlaylist.getIsPublic())
                .build();
    }

    @Override
    public ApiResponse deletePlaylistBySlug(String slug) {
        PlayList foundPlaylist = playlistRepository.findBySlug(slug).orElseThrow(PlaylistNotFoundException::new);
        playlistRepository.delete(foundPlaylist);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public ApiResponse deletePlaylistById(Long playlistId) {
        PlayList foundPlaylist = playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
        playlistRepository.delete(foundPlaylist);
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESSFUL")
                .build();
    }

    @Override
    public FindPlaylistResponse updatePlaylistDetails(Long playlistId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        PlayList foundPlaylist = playlistRepository.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);
        //Playlist Object to node
        JsonNode node = mapper.convertValue(foundPlaylist, JsonNode.class);
        try {
            //apply patch
            JsonNode updatedNode = updatePayload.apply(node);
            //node to Playlist Object
            var updatedPlaylist = mapper.convertValue(updatedNode, PlayList.class);
            PlayList newUpdatedPlaylist = playlistRepository.save(updatedPlaylist);
            return FindPlaylistResponse.builder()
                    .id(newUpdatedPlaylist.getId())
                    .name(newUpdatedPlaylist.getName())
                    .coverImage(newUpdatedPlaylist.getCoverImage())
                    .description(newUpdatedPlaylist.getDescription())
                    .slug(newUpdatedPlaylist.getSlug())
                    .isPublic(newUpdatedPlaylist.getIsPublic())
                    .build();

        } catch (JsonPatchException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    private Optional<PlayList> privateFindPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    private void updatePlaylistProfileImage(String url, PlayList playList) {
        playList.setCoverImage(url);
        playlistRepository.save(playList);
    }

}
