package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Match;
import quiniela.model.Player;

import java.util.List;

@Service
public interface PlayerService {

    List<String> getAllPlayersUsername();

    List<Player> getAllPlayers();

    Player validateUsername(String userName, String password);

    Player getPlayerByUsername(String username);

    Player createPlayer(String username, String password);

    Player updatePlayer(Player player);
}
