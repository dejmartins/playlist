package africa.semicolon.playlist.playlist.repository;

import africa.semicolon.playlist.playlist.demo.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlayList, Long> {

    Optional<PlayList> findBySlug(String slug);

    Optional<PlayList> findByName(String name);

}
