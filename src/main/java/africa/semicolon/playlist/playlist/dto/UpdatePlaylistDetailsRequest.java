package africa.semicolon.playlist.playlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlaylistDetailsRequest {

    private Long playlistId;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String slug;
    @NotNull
    private Boolean isPublic;
}
