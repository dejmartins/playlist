package africa.semicolon.playlist.playlist.subscription.repository;


import africa.semicolon.playlist.playlist.demo.PlayList;
import africa.semicolon.playlist.playlist.subscription.demoSubscriber.Subscriber;
import africa.semicolon.playlist.user.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Optional<List<Subscriber>> findAllByUser(UserEntity userEntity);

    List<Subscriber> findAllByPlayList(PlayList playList);

    Optional<Subscriber> findByUserAndPlayList(UserEntity userEntity, PlayList playList);
}
