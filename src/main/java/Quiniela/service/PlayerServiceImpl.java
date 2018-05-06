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

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Value("${ladder.password}")
    String genericLadderPassword;

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @Value("${encryption.algorithm}")
    String algorithmEncode;

    @PostConstruct
    private void init() {
        if(cleanAndBuild) {
            playerRepository.deleteAll();

            Player p = createPlayer(genericUsername, genericPassword);
            //String token = loginService.login(genericUsername, genericPassword);
            ladderBoardService.createLadderBoard(genericLaddername, genericLadderPassword, p);
            ladderBoardService.createLadderBoard("PRUEBA", null, p);
        }
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
