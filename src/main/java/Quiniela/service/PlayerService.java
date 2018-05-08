package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Match;
import quiniela.model.Player;

import java.util.List;

@Service
public interface PlayerService {

    Player createPlayer(String username, String password);

    Player getPlayerByUsername(String username);

    void resetPassword(String username);
}
