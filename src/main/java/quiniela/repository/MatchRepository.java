package quiniela.repository;

import quiniela.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface MatchRepository extends MongoRepository<Match, Long> {
}
