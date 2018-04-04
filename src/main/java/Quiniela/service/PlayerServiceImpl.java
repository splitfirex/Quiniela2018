package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import quiniela.model.Match;
import quiniela.model.Player;
import quiniela.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
@Service
public class PlayerServiceImpl implements PlayerService {

    private static MessageDigest sha;

    private AtomicLong counter = new AtomicLong();

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    MatchService matchService;

    @Value("${player.username}")
    String genericUsername;

    @Value("${player.password}")
    String genericPassword;

    @Value("${encryption.algorithm}")
    String algorithmEncode;

    @PostConstruct
    private void init() {
        playerRepository.deleteAll();
        try {
            sha = MessageDigest.getInstance(algorithmEncode);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Player p =  new Player();
        p.setId(counter.incrementAndGet());
        p.addMatches(matchService.getAllMatches());
        p.setUsername(genericUsername);
        sha.update(genericPassword.getBytes());
        p.setPassword(Base64.getEncoder().encodeToString( sha.digest()));
        playerRepository.save(p);

    }

    public Player validateUsername(String userName, String password){
        sha.update(password.getBytes());
        return playerRepository.validateUser(userName,Base64.getEncoder().encodeToString( sha.digest()));
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

        if(genericUsername.equals(username)) return null;

        Player p =  new Player();
        p.setId(counter.incrementAndGet());
        p.addMatches(matchService.getAllMatches());
        p.setUsername(username);
        sha.update(password.getBytes());
        p.setPassword(Base64.getEncoder().encodeToString( sha.digest()));
        return playerRepository.save(p);
    }

    @Override
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }


}
