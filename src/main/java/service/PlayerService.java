package service;

import model.Match;
import model.Player;

import java.util.List;

public interface PlayerService {

    static PlayerService instance = new PlayerServiceImpl();

    List<Match> updatePlayerMatch(String userName, String password, Match updatedMatch);

    List<String> getAllPlayersUsername();

    List<Player> getAllPlayers();

    Player validateUsername(String userName, String password);

    Player getPlayerByUsername(String username);
}
