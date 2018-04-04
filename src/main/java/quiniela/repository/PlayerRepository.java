package quiniela.repository;

import quiniela.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface PlayerRepository extends MongoRepository<Player, Long> {


}
