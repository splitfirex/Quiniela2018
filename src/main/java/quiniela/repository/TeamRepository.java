package quiniela.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.Group;
import quiniela.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface TeamRepository extends MongoRepository<Team, Long> {

    @Query("{ 'name' : ?0 }")
    Team findByName(String name);

    @Query("{ 'id' : ?0 }")
    Team findById(long id);

    @Query("{ }")
    List<Team> findAllSort(Sort sort);

}
