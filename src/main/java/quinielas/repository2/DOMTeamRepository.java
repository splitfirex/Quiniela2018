package quinielas.repository2;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMTeam;

import java.util.List;

@Repository
public interface DOMTeamRepository extends MongoRepository<DOMTeam, Long> {
    @Query("{ }")
    List<DOMTeam> findAllOrdered( Sort sort);
}
