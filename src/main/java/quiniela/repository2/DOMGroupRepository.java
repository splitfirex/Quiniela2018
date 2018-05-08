package quiniela.repository2;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.PlayerMatch;
import quiniela.utils.dom.DOMGroup;
import quiniela.utils.dom.DOMTeam;

import java.util.List;

@Repository
public interface DOMGroupRepository extends MongoRepository<DOMGroup, String> {

    @Query("{ idPlayer: ?0, idLadder: ?1}")
    List<DOMGroup> findAllByIdPlayerAndIdLadder(Long idUser, Long idLadder, Sort sort);

    @Query("{ idPlayer: ?0, idLadder: ?1, idMatch: ?2}")
    DOMGroup findOneByIdPlayerAndIdLadderAndIdMatch(Long idUser, Long idLadder, Long idMatch);

    @Query("{idMatch:{$in: ?0 },  idPlayer: ?1, idLadder: ?2}")
    List<DOMGroup> findAllByIdMatchesAndIdPlayerAndIdLadder(List<Long> ids, Long idUser, Long idLadder, Sort sort);

}
