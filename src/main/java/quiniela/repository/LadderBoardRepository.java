package quiniela.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quiniela.model.LadderBoard;

import java.util.List;

@Repository
public interface LadderBoardRepository extends MongoRepository<LadderBoard, Long>, LadderBoardCustomRepository {

    @Query("{ 'name' : ?0 }")
    LadderBoard findByName(String name);

    @Query("{ 'id' : ?0 }")
    LadderBoard findById(long id);

    @Query("{ 'password' : { '$exists' : false } }")
    List<LadderBoard> findPublic();

    @Query("{ 'listPlayers.username' : ?0, 'listPlayers.isActive': ?1 } }")
    List<LadderBoard> findByUsername(String username, boolean active);

    @Query(value = "{}", fields = "{ 'name' : 1, 'listPlayers.username' : 1 }")
    List<LadderBoard> findAllShort();

    @Query("{ 'name': ?0, 'listPlayers.username' : ?1, 'listPlayers.isActive': true }")
    LadderBoard findByIdAndActive(String ladderName, String username);

    @Query("{ 'name': ?0, 'password' : { '$exists' : false } }")
    LadderBoard findIfPublic(String ladderName);

    @Query("{ 'name': ?0, 'password' : { '$exists' : true } }")
    LadderBoard isPasswordLadderBoard(String ladderName);






}
