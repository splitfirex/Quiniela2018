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

        Player p = new Player();

        p.setId(counter.incrementAndGet());
        p.setUsername("_NOT_A_PLAYER_");
        p.setPassword(loginService.encode("_NOT_A_PLAYER_"));
        playerRepository.save(p);

        LadderBoard l = ladderBoardService.getTournamentByName("_NOT_A_LADDERBOARD_");
        ladderBoardService.addPlayer(l,p,TypePlayerState.ACTIVE);
        ladderBoardService.setAdmin(l,p, true);

        p = new Player();
        p.setId(counter.incrementAndGet());
        p.setUsername("Daniel");
        p.setPassword(loginService.encode("123456"));
        playerRepository.save(p);


    }

    @Override
    public Player getPlayerByUsername(String username) {
        return  playerRepository.findByUsername(username);
    }

    @Override
    public List<String> getAllPlayersUsername() {
        List<String> result = new ArrayList<>();
        for(Player p : playerRepository.findAll()){
            result.add(p.getUsername());
        }

        return result;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player createPlayer(String username, String password) {

        if(playerRepository.findByUsername(username) != null) return null;

        Player p =  new Player();
        p.setId(counter.incrementAndGet());
        p.setUsername(username);
        p.setPassword(loginService.encode(password));

        return playerRepository.save(p);
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player activateDeactivatePlayer(String username, String token, LadderBoard t, boolean set ) {
        Player preAdmin = loginService.getPlayerByToken(token);
        if(ladderBoardService.getAdminsByIdTournament(t.getId()).contains(preAdmin.getUsername())){
            if(set) {
                ladderBoardService.addPlayer(t, playerRepository.findByUsername(username), TypePlayerState.ACTIVE);
            }else{
                ladderBoardService.addPlayer(t, playerRepository.findByUsername(username), TypePlayerState.INACTIVE);
            }
        }
        return  playerRepository.findByUsername(username);

    }


}
