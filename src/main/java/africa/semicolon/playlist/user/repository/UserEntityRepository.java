package africa.semicolon.playlist.user.repository;

import africa.semicolon.playlist.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
