package quiniela.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;

import java.util.List;

@Repository
public interface PlayerMatchRepositoty extends MongoRepository<PlayerMatch, Long> {

    @Query("{ idPlayer: ?0, idLadder: ?1}")
    List<PlayerMatch> findAllByUserIdAndLadderboardID(Long idUser, Long idLadder, Sort sort);

    @Query("{ idPlayer: ?0, idLadder: ?1, idMatch: ?2}")
    PlayerMatch findOneByUserIdAndLadderboardID(Long idUser, Long idLadder, Long idMatch);

}
