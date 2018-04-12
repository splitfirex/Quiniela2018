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

    @PostConstruct
    private void init() {
        ladderBoardRepository.deleteAll();
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
        LadderBoard t =  new LadderBoard();
        t.setName(name);
        t.setPassword(loginService.encode(password));
        t.setId(counter.incrementAndGet());

        Player p = loginService.getPlayerByToken(token);
        ladderBoardRepository.save(t);
        addPlayer(t.getId(),p, TypePlayerState.ACTIVE);
        return t;
    }

    @Override
    public List<LadderBoard> getLadderBoardsByUsername(String username) {
        return ladderBoardRepository.findByUsername(username);
    }

    @Override
    public LadderBoard addPlayer(long idTournament, Player player, TypePlayerState state) {
        LadderBoard t =  ladderBoardRepository.findById(idTournament);
        t.getListPlayers().put(player.getUsername(),state);
        ladderBoardRepository.save(t);
        return t;
    }

    @Override
    public LadderBoard removePlayer(long idTournament, Player player) {
        LadderBoard t =  ladderBoardRepository.findById(idTournament);
        t.getListPlayers().remove(player.getUsername());
        ladderBoardRepository.save(t);
        return null;
    }

    @Override
    public LadderBoard setAdmin(long idTournament, Player player, boolean set) {
        LadderBoard t =  ladderBoardRepository.findById(idTournament);
        if(set){
            t.getListAdmins().add(player.getUsername());
        }else{
            t.getListAdmins().remove(player.getUsername());
        }
        ladderBoardRepository.save(t);
        return t ;
    }
}
