package africa.semicolon.playlist.playlist.repository;

import africa.semicolon.playlist.playlist.demo.PlayList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlayList, Long> {

    Optional<PlayList> findBySlug(String slug);

    Optional<PlayList> findByName(String name);

}
