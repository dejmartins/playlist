package africa.semicolon.playlist.user.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    @Column(unique = true, nullable = false)
    private String emailAddress;
    private final LocalDateTime dateJoined = LocalDateTime.now();

}
