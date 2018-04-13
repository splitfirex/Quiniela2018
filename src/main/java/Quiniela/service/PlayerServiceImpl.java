package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Match;
import quiniela.model.Player;
import quiniela.model.enums.TypePlayerState;
import quiniela.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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

        ladderBoardService.createLadderBoard("_NOT_A_LADDERBOARD_",p);
        ladderBoardService.joinLadderBoard("_NOT_A_LADDERBOARD_",p2);
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
}
