package quinielas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import quinielas.model.LadderBoard;

import java.util.List;

@Repository
public interface LadderBoardRepository extends MongoRepository<LadderBoard, Long>, LadderBoardCustomRepository {

    @Query("{ 'name' : ?0 }")
    LadderBoard findByName(String name);


    @Query("{ 'listPlayers.username' : ?0, 'listPlayers.isActive': ?1 } }")
    List<LadderBoard> findByUsername(String username, boolean active);

    @Query(value = "{}", fields = "{ 'name' : 1, 'type': 1, 'bgColor': 1, 'listPlayers.username' : 1 , 'listPlayers.isActive': 1, 'listPlayers.isAdmin': 1, 'password': 1 }")
    List<LadderBoard> findAllShort();






}
