package africa.semicolon.playlist.playlist.Contributor.repository;

import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.Contributor.demoContributor.Contributor;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    Optional<List<Contributor>> findByUser(UserEntity userEntity);

    Optional<List<Contributor>> findByPlayList(PlayList playList);

    Optional<Contributor> findByUserAndPlayList(UserEntity userEntity, PlayList playList);

}
