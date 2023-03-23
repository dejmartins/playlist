package africa.semicolon.playlist.playlist.repository;

import africa.semicolon.playlist.playlist.demo.PlayList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<PlayList, Long> {

    PlayList findBySlug(String slug);

    PlayList findByName(String name);
}
