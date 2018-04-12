package quiniela.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import quiniela.model.LadderBoard;

import java.util.List;


public interface LadderBoardRepository extends MongoRepository<LadderBoard, Long> {

    @Query("{ 'name' : ?0 }")
    LadderBoard findByName(String name);

    @Query("{ 'id' : ?0 }")
    LadderBoard findById(long id);

    @Query("{ 'listPlayers' : ?0 }")
    List<LadderBoard> findByUsername(String username);

    @Query("{ 'password' : { '$exists' : false } }")
    List<LadderBoard> findPublic();
}
