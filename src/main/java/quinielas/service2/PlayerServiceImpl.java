package quinielas.service2;

import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quinielas.model.Player;
import quinielas.repository.PlayerRepository;
import quinielas.utils.RESTClient;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PlayerServiceImpl implements PlayerService {

    private AtomicLong counter = new AtomicLong();
    private static Map<UUID,Player> uuidPlayer = new HashMap<>();
    private static MessageDigest sha;

    private static final long EXPIDED_MINUTES = 10;

    private static final long RENO_MINUTES = 5;

    @Value("${encryption.algorithm}")
    String algorithmEncode;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    LadderboardService ladderBoardService;



    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Value("${player.username}")
    String genericUsername;

    @Value("${player.password}")
    String genericPassword;

    @Value("${ladder.password}")
    String genericLadderPassword;

    @PostConstruct
    private void init() {
        if(cleanAndBuild) {
            playerRepository.deleteAll();
            Player p = createPlayer(genericUsername, genericPassword, false);
            ladderBoardService.createLadderBoard(genericLaddername, genericLadderPassword, p, "Completo",false);

            p = createPlayer("Jugador 1", "jugador", true);
            ladderBoardService.createLadderBoardDemo("DEMO", null, p);
            p = createPlayer("Jugador 2", "jugador", true);
            ladderBoardService.joinLadderBoardDemo("DEMO", null, p);

        }
    }

    @Override
    public String encode(String value){
        if(sha == null){
            try {
                sha = MessageDigest.getInstance(algorithmEncode);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return byteArrayToHexString(sha.digest(value.getBytes()));
    }

    public static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;
    public static long getTimeFromUUID(UUID uuid) {
        return (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
    }

    @Override
    public String login(String username, String password) {
        revalidateTokens();

        if(uuidPlayer.values().contains(username)){
            for(UUID uuid : uuidPlayer.keySet()) {
                if (uuidPlayer.get(uuid).equals(username)) {
                    uuidPlayer.remove(uuid);
                    break;
                }
            }
        }

        Player p = playerRepository.validateUser(username,encode(password));
        if( p != null){
            UUID uuid = Generators.timeBasedGenerator().generate();
            uuidPlayer.put(uuid,p);
            return uuid.toString();
        }
        return null;
    }

    @Override
    public String validateLogin(String token) {
        revalidateTokens();

        if(uuidPlayer.containsKey(UUID.fromString(token))){
            return renovateToken(UUID.fromString(token));
        }

        return null;
    }

    @Override
    public Boolean logout(String token) {

        revalidateTokens();

        if(uuidPlayer.containsKey(UUID.fromString(token))){
            uuidPlayer.remove(UUID.fromString(token));
            return true;
        }

        return false;
    }

    @Override
    public Player getPlayerByToken(String token) {
        if (token == null) return null;
        return uuidPlayer.get(UUID.fromString(token));
    }

    @Override
    public Player createPlayer(String username, String password, boolean encode) {
        Player pp = playerRepository.findByUsername(username);
        if(pp != null && pp.getPassword().equals("")){
            pp.setPassword(encode(password));
            playerRepository.save(pp);
            return pp;
        }
        if(playerRepository.findByUsername(username) != null) return null;
        Player p = new Player();
        p.setId(Generators.randomBasedGenerator().generate().getLeastSignificantBits());
        p.setUsername(username);
        if(encode){
            p.setPassword(encode(password));
        }else{
            p.setPassword(password);
        }
        playerRepository.save(p);

        return  p;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    @Override
    public void resetPassword(String username) {
        Player pp = playerRepository.findByUsername(username);
        if(pp != null){
            pp.setPassword("");
            playerRepository.save(pp);
        }
    }

    private void  revalidateTokens(){
        Iterator itTokens = uuidPlayer.keySet().iterator();
        while(itTokens.hasNext()){
            UUID uuid = (UUID) itTokens.next();

            if(Instant.now().compareTo(Instant.ofEpochMilli(getTimeFromUUID(uuid)).plus(EXPIDED_MINUTES,ChronoUnit.MINUTES)) > 1){
                uuidPlayer.remove(uuid);
            }
        }
    }

    private String renovateToken(UUID token){
        if(Instant.now().compareTo(Instant.ofEpochMilli(token.timestamp()).plus(RENO_MINUTES,ChronoUnit.MINUTES)) > 1){
            UUID uuid = Generators.timeBasedGenerator().generate();
            uuidPlayer.put(uuid,uuidPlayer.get(token));
            uuidPlayer.remove(token);
            return uuid.toString();
        }

        return token.toString();
    }
}
