package africa.semicolon.playlist.user.data.repositories;

import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmailAddress(String emailAddress);

    Optional<UserEntity> findByEmailAddress(String emailAddress);

    Optional<UserEntity> findByUsername(String username);
}
