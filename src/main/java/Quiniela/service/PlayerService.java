package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Match;
import quiniela.model.Player;

import java.util.List;

@Service
public interface PlayerService {

    static PlayerService instance = new PlayerServiceImpl();

    List<Match> updatePlayerMatch(String userName, String password, Match updatedMatch);

    List<String> getAllPlayersUsername();

    List<Player> getAllPlayers();

    Player validateUsername(String userName, String password);

    Player getPlayerByUsername(String username);
}
