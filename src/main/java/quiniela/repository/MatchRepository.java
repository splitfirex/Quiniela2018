package quiniela.repository;

import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<Match, Long> {

    @Query("{ 'idPlayer' : { '$exists' : false } }")
    List<Match> findAllFixtures(Sort sort);

}
