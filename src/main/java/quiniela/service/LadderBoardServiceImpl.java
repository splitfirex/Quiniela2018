package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.LadderBoardPlayer;
import quiniela.model.Player;
import quiniela.repository.LadderBoardRepository;
import quiniela.repository.PlayerMatchRepositoty;

import javax.annotation.PostConstruct;
import java.util.List;
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
    public LadderBoard createLadderBoard(String name, String password, Player p) {
        if (ladderBoardRepository.findByName(name) != null) return null;
        LadderBoard l = new LadderBoard();
        l.setName(name);
        l.setPassword(password != null ? loginService.encode(password) : null);
        l.setId(counter.incrementAndGet());
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), true, true));

        matchService.createPlayerMatches(l,p);
        groupService.createPlayerGroup(l,p);
        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public LadderBoard joinLadderBoard(String name, String password, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(name);
        if (l == null || l.getPlayerByName(p.getUsername()) != null) return null;
        if (l.getPassword() != null && (password == null || !l.getPassword().equals(loginService.encode(password))))
            return null;
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), false));

        matchService.createPlayerMatches(l,p);
        groupService.createPlayerGroup(l,p);
        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(ladderBoard.getName());
        if (l == null || l.getPlayerByName(p.getUsername()) == null) return null;

        if (l.getPlayerByName(p.getUsername()) != null) {
            int countAdmin = 0;
            boolean isAdmin = false;

            for (LadderBoardPlayer user : l.getListPlayers()) {
                if (user.getAdmin() == true) countAdmin += 1;
                if (user.getUsername().equals(p.getUsername())) isAdmin = true;
            }
            l.getListPlayers().remove(l.getPlayerByName(p.getUsername()));
            if(isAdmin && countAdmin == 1){
                if (l.getListPlayers().size() > 1) {
                    l.getListPlayers().get(0).setAdmin(true);
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
        return ladderBoardRepository.findAllShort();
    }

    @Override
    public List<LadderBoard> listLadderBoard(Player p) {
        if (p == null) return null;
        return ladderBoardRepository.findByUsername(p.getUsername(), false);
    }

    @Override
    public LadderBoard getLadderBoardByName(String name, Player p) {
        return ladderBoardRepository.findByIdOrIfActive(name, p == null ? null : p.getUsername());
    }

    @Override
    public LadderBoard getLadderBoard(String name) {
        return ladderBoardRepository.findByName(name);
    }
}
