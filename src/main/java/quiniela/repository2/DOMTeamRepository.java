package quiniela.repository2;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import quiniela.utils.dom.DOMTeam;

@Repository
public interface DOMTeamRepository extends MongoRepository<DOMTeam, Long> {
}
