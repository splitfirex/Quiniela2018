package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Player;

@Service
public interface LoginService {

    String login(String username, String password);

    Player createPlayer(String username, String password);

    String validateLogin(String token);

    Boolean logout(String token);

    Player getPlayerByToken(String token);

    String encode(String value);
}
