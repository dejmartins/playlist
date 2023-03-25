package africa.semicolon.playlist.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlaylistResponse {

    private Long id;
    private String name;
    private String description;
    private String slug;
    private String coverImage;
    private Boolean isPublic;

}
