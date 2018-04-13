package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import quiniela.model.Player;
import quiniela.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Service
public class PlayerServiceImpl implements PlayerService {

    private AtomicLong counter = new AtomicLong();

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    LadderBoardService ladderBoardService;

    @Autowired
    MatchService matchService;

    @Autowired
    LoginService loginService;

    @Value("${player.username}")
    String genericUsername;

    @Value("${player.password}")
    String genericPassword;

    @Value("${encryption.algorithm}")
    String algorithmEncode;

    @PostConstruct
    private void init() {
        playerRepository.deleteAll();

        Player p = createPlayer("_NOT_A_PLAYER", "_NOT_A_PLAYER");
        String token = loginService.login("_NOT_A_PLAYER", "_NOT_A_PLAYER");

        Player p2 = createPlayer("Daniel", "Daniel");

        ladderBoardService.createLadderBoard("_NOT_A_LADDERBOARD_", null, p);
        ladderBoardService.createLadderBoard("_NOT_A_LADDERBOARD2_", "1234", p);

        ladderBoardService.joinLadderBoard("_NOT_A_LADDERBOARD_", "1234", p2);
    }

    @Override
    public Player createPlayer(String username, String password) {
        if(playerRepository.findByUsername(username) != null) return null;

        Player p = new Player();
        p.setId(counter.incrementAndGet());
        p.setUsername(username);
        p.setPassword(loginService.encode(password));
        playerRepository.save(p);

        return  p;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }
}
