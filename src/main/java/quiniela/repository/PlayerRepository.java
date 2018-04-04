package quiniela.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@Repository
public interface PlayerRepository extends MongoRepository<Player, Long> {

    @Query("{ username: ?0, password: ?1}")
    Player validateUser(String username, String password);

    @Query("{ username: ?0 }")
    Player findByUsername(String username);
}
