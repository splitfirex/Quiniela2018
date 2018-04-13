package quiniela.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.PlayerGroup;
import quiniela.model.PlayerMatch;

import java.util.List;

@Repository
public interface PlayerGroupRepository extends MongoRepository<PlayerGroup, Long> {

    @Query("{ idPlayer: ?0, idLadder: ?1}")
    List<PlayerGroup> findAllByUserIdAndLadderboardID(Long idUser, Long idLadder);

}
