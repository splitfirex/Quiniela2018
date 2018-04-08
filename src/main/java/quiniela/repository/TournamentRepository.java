package quiniela.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import quiniela.model.Player;
import quiniela.model.Team;
import quiniela.model.Tournament;

public interface TournamentRepository extends MongoRepository<Tournament, Long> {

    @Query("{ 'name' : ?0 }")
    Tournament findByName(String name);

    @Query("{ 'id' : ?0 }")
    Tournament findById(long id);

    @Query("{ 'listPlayers' : ?0 }")
    Tournament findByUsername(String username);
}
