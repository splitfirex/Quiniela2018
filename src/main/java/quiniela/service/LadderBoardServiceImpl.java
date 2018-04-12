package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.enums.TypePlayerState;
import quiniela.repository.LadderBoardRepository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LadderBoardServiceImpl implements LadderBoardService {

    static private AtomicLong counter = new AtomicLong();

    @Autowired
    private LadderBoardRepository ladderBoardRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MatchService matchService;

    @PostConstruct
    private void init() {
        ladderBoardRepository.deleteAll();
        LadderBoard l = new LadderBoard();
        l.setId(counter.incrementAndGet());
        l.setName("_NOT_A_LADDERBOARD_");

        ladderBoardRepository.save(l);
    }

    @Override
    public List<LadderBoard> getAllTournaments() {
        return ladderBoardRepository.findAll();
    }

    @Override
    public Map<String, TypePlayerState> getPlayersByIdTournament(long idTournament) {
        return ladderBoardRepository.findById(idTournament).getListPlayers();
    }

    @Override
    public List<String> getAdminsByIdTournament(long idTournament) {
        return ladderBoardRepository.findById(idTournament).getListAdmins();
    }

    @Override
    public LadderBoard updateLadderBoard(LadderBoard l) {
        return ladderBoardRepository.save(l);
    }

    @Override
    public LadderBoard getTournamentByName(String name) {
        return ladderBoardRepository.findByName(name);
    }

    @Override
    public LadderBoard getTournamentById(Long id) {
        return ladderBoardRepository.findById(id).orElse(null);
    }

    @Override
    public LadderBoard createTournament(String name, String token, String password) {
        if(ladderBoardRepository.findByName(name) != null) return null;

        LadderBoard t =  new LadderBoard();
        t.setName(name);
        t.setPassword(loginService.encode(password));
        t.setId(counter.incrementAndGet());

        Player p = loginService.getPlayerByToken(token);
        addPlayer(t,p, TypePlayerState.ACTIVE);
        setAdmin(t,p,true);

        return t;
    }


    @Override
    public List<LadderBoard> getPublicLadderBoard() {
        return ladderBoardRepository.findPublic();
    }

    @Override
    public LadderBoard addPlayer(LadderBoard t, Player player, TypePlayerState state) {
        if(t.getListPlayers().keySet().contains(player.getUsername())) return null;
        t.addPlayer(player,matchService.getPlayerMatches(),state);
        ladderBoardRepository.save(t);
        return t;
    }

    @Override
    public LadderBoard removePlayer(LadderBoard t, Player player) {
        t.removePlayer(player);
        ladderBoardRepository.save(t);
        return t;
    }

    @Override
    public LadderBoard setAdmin(LadderBoard t, Player player, boolean set) {
        if(set){
            if(t.getListAdmins().contains(player.getUsername())) return null;
            t.getListAdmins().add(player.getUsername());
        }else{
            if(!t.getListAdmins().contains(player.getUsername())) return null;
            t.getListAdmins().remove(player.getUsername());
        }
        ladderBoardRepository.save(t);
        return t ;
    }
}
