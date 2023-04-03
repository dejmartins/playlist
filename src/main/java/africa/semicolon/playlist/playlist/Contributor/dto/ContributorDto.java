package africa.semicolon.playlist.playlist.Contributor.dto;

import africa.semicolon.playlist.user.dto.response.UserDto;
import lombok.Data;

@Data
public class ContributorDto {

    private Long id;
    private UserDto user;
    private boolean isAuthor;

}
