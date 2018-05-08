package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.model.Player;

@Service
public interface PlayerService {

    String login(String username, String password);

    String validateLogin(String token);

    Boolean logout(String token);

    Player getPlayerByToken(String token);

    Player createPlayer(String username, String password);

    Player getPlayerByUsername(String username);

    void resetPassword(String username);

    String encode(String value);

}
