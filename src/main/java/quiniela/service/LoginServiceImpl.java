package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quiniela.model.Player;
import quiniela.repository.PlayerRepository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    private static Map<UUID,Player> uuidPlayer = new HashMap<>();
    private static Map<String, UUID> uuidToken = new HashMap<>();
    private static MessageDigest sha;

    private static final long EXPIDED_MINUTES = 10;

    private static final long RENO_MINUTES = 5;



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
    }

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

    @Override
    public String encode(String value){
        sha.update(value.getBytes());
        return Base64.getEncoder().encodeToString( sha.digest());
    }

    @Override
    public String login(String username, String password) {
        revalidateTokens();

        Player p = playerRepository.validateUser(username,encode(password));
        if( p != null){
            UUID uuid = UUID.randomUUID();
            uuidPlayer.put(uuid,p);
            uuidToken.put(uuid.toString(), uuid);
            return uuid.toString();
        }
        return null;
    }

    @Override
    public Player createPlayer(String username, String password) {
        return playerService.createPlayer(username,password);
    }

    @Override
    public String validateLogin(String token) {
        revalidateTokens();

        if(uuidToken.containsKey(token)){
            return renovateToken(uuidToken.get(token));
        }

        return null;
    }

    @Override
    public Boolean logout(String token) {

        revalidateTokens();

        if(uuidToken.containsKey(token)){
            uuidToken.remove(token);
            return true;
        }

        return false;
    }

    @Override
    public Player getPlayerByToken(String token) {
        return uuidPlayer.get(uuidToken.get(token));
    }

    private void  revalidateTokens(){
        Iterator itTokens = uuidToken.values().iterator();
        while(itTokens.hasNext()){
            UUID uuid = (UUID) itTokens.next();

            if(Instant.now().compareTo(Instant.ofEpochMilli(uuid.timestamp()).plus(EXPIDED_MINUTES,ChronoUnit.MINUTES)) > 1){
                uuidToken.remove(uuid.toString());
            }
        }
    }

    private String renovateToken(UUID token){
        if(Instant.now().compareTo(Instant.ofEpochMilli(token.timestamp()).plus(RENO_MINUTES,ChronoUnit.MINUTES)) > 1){
            UUID uuid = UUID.randomUUID();
            uuidToken.put(uuid.toString(),uuid);
            uuidPlayer.put(uuid,uuidPlayer.get(token));

            uuidToken.remove(token.toString());
            uuidPlayer.remove(token);
            return uuid.toString();
        }

        return token.toString();
    }

}
