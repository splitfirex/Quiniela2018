package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.LadderBoardPlayer;
import quiniela.model.Player;
import quiniela.model.enums.TypePlayerState;
import quiniela.repository.LadderBoardRepository;
import quiniela.repository.PlayerMatchRepositoty;

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
    private PlayerMatchRepositoty playerMatchRepositoty;

    @Autowired
    private LoginService loginService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MatchService matchService;

    @PostConstruct
    private void init() {
        ladderBoardRepository.deleteAll();
    }


    @Override
    public LadderBoard createLadderBoard(String name, Player p) {
        if(ladderBoardRepository.findByName(name)!= null) return null;
        LadderBoard l = new LadderBoard();
        l.setId(counter.incrementAndGet());
        l.getListPlayers().put(p.getUsername(),new LadderBoardPlayer(p.getId(), true,true));

        matchService.createPlayerMatches(l,p);
        groupService.createPlayerGroup(l,p);
        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public LadderBoard joinLadderBoard(String name, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(name);
        if(l == null || l.getListPlayers().containsKey(p.getUsername())) return null;
        l.getListPlayers().put(p.getUsername(),new LadderBoardPlayer(p.getId(), false));

        matchService.createPlayerMatches(l,p);
        groupService.createPlayerGroup(l,p);
        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(ladderBoard.getName());
        if(l == null || !l.getListPlayers().containsKey(p.getUsername())) return null;

        if(l.getListPlayers().keySet().contains(p.getUsername())){
            int countAdmin = 0;
            boolean isAdmin = false;

            for(LadderBoardPlayer user: l.getListPlayers().values()){
                if(user.isAdmin() == true)countAdmin +=1;
                if(user.getId().equals(p.getId()))isAdmin = true;
            }
            l.getListPlayers().remove(p.getUsername());
            if(isAdmin && countAdmin == 1){
                if(l.getListPlayers().keySet().size() > 1){
                    String newAdmin = l.getListPlayers().keySet().toArray(new String[l.getListPlayers().keySet().size()])[0];
                    l.getListPlayers().get(newAdmin).setAdmin(true);
                }
            }


            matchService.deletePlayerMatches(l,p);
            groupService.deletePlayerGroup(l,p);
        }

        ladderBoardRepository.save(l);
        if(l.getListPlayers().size() == 0){
            ladderBoardRepository.delete(l);
        }

        return l;
    }

    @Override
    public List<LadderBoard> listLadderBoard() {
        return ladderBoardRepository.findAll();
    }

    @Override
    public List<LadderBoard> listLadderBoard(Player p) {
        return ladderBoardRepository.findByUserId(p.getId());
    }
}
