package africa.semicolon.playlist.user.repositories;

import africa.semicolon.playlist.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmailAddress(String emailAddress);
    Optional<User> findUserByEmailAddress(String emailAddress);

}
