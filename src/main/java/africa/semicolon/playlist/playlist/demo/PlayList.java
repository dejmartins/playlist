package africa.semicolon.playlist.playlist.demo;

import africa.semicolon.playlist.playlist.plan.PlanType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlayList {

    @Id
    private Long id;
    private String name;
    private String description;
    private String slug;
    private PlanType plantype;
    private String coverImage;
    private boolean isPublic;

}

