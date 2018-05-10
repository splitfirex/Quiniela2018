package quinielas.service2;

import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.LadderBoardPlayer;
import quinielas.model.Player;
import quinielas.repository.LadderBoardRepository;
import quinielas.utils.dom.DOMMatch;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LadderboardServiceImpl implements LadderboardService{

    @Autowired
    LadderBoardRepository ladderBoardRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    GroupService groupService;

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @Value("${ladder.laddername}")
    String genericLaddername;

    private Long genericladderid;

    @PostConstruct
    private void init() {
        if(cleanAndBuild) {
            ladderBoardRepository.deleteAll();
        }else{
            genericladderid = ladderBoardRepository.findByName(genericLaddername).getId();
        }
    }

    @Override
    public LadderBoard createLadderBoard(String name, String password, Player p, boolean encode) {
        LadderBoard ladder = ladderBoardRepository.findByName(name);
        if(ladder != null &&  ladder.getPassword() != null &&ladder.getPassword().equals("")){
            ladder.setPassword(password != null ? playerService.encode(password) : null);
            ladderBoardRepository.save(ladder);
            return ladder;
        }

        if (ladderBoardRepository.findByName(name) != null) return null;
        LadderBoard l = new LadderBoard();
        l.setName(name);
        if(encode) {
            l.setPassword(password != null ? playerService.encode(password) : null);
        }else{
            l.setPassword(password != null ? password : null);
        }
        l.setId(Generators.randomBasedGenerator().generate().getLeastSignificantBits());
        if(name.equals(genericLaddername)) genericladderid = l.getId();
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), true, true));
        Random rand = new Random();
        int nextInt = rand.nextInt(256*256*256);
        l.setBgColor(String.format("#%06x", nextInt));

        groupService.generateGroupsForPlayerAndLadder(p.getId(),l.getId());

        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public LadderBoard joinLadderBoard(String name, String password, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(name);
        if (l == null || l.getPlayerByName(p.getUsername()) != null) return null;
        if (l.getPassword() != null && (password == null || !l.getPassword().equals(playerService.encode(password))))
            return null;
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), false));

        groupService.generateGroupsForPlayerAndLadder(p.getId(),l.getId());

        ladderBoardRepository.save(l);
        return l;
    }

    @Override
    public void createLadderBoardDemo(String name, String password, Player p) {
        LadderBoard l = new LadderBoard();
        l.setName(name);
        l.setPassword(password != null ? playerService.encode(password) : null);
        l.setId(Generators.randomBasedGenerator().generate().getLeastSignificantBits());
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), true, true));
        Random rand = new Random();
        int nextInt = rand.nextInt(256*256*256);
        l.setBgColor(String.format("#%06x", nextInt));

        groupService.generateDemoGroupsForPlayerAndLadder(p.getId(),l.getId());
        l.getPlayerByName(p.getUsername()).setWinnerTeam(groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId()).stream().filter(f -> f.getId() == 64L).findFirst().get().calculateWinner());
        ladderBoardRepository.save(l);
    }

    @Override
    public void joinLadderBoardDemo(String name, String password, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(name);
        l.getListPlayers().add(new LadderBoardPlayer(p.getUsername(), true));
        groupService.generateDemoGroupsForPlayerAndLadder(p.getId(),l.getId());
        l.getPlayerByName(p.getUsername()).setWinnerTeam(groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId()).stream().filter(f -> f.getId() == 64L).findFirst().get().calculateWinner());
        ladderBoardRepository.save(l);
    }

    @Override
    public LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p) {
        LadderBoard l = ladderBoardRepository.findByName(ladderBoard.getName());
        if (l == null || l.getPlayerByName(p.getUsername()) == null) return null;

        if (l.getPlayerByName(p.getUsername()) != null) {
            int countAdmin = 0;
            boolean isAdmin = false;

            for (LadderBoardPlayer user : l.getListPlayers()) {
                if (user.getAdmin() != null && user.getAdmin() == true) countAdmin += 1;
                if (user.getUsername().equals(p.getUsername())) isAdmin = true;
            }
            l.getListPlayers().remove(l.getPlayerByName(p.getUsername()));
            if(isAdmin && countAdmin == 1){
                if (l.getListPlayers().size() > 1) {
                    LadderBoardPlayer lbp = l.getListPlayers().iterator().next();
                    lbp.setActive(true);
                    lbp.setAdmin(true);
                }
            }

        }

        groupService.deleteGroupsForPlayerAndLadder(p.getId(),l.getId());

        ladderBoardRepository.save(l);
        if(l.getListPlayers().size() == 0){
            ladderBoardRepository.delete(l);
        }

        return l;
    }

    @Override
    public List<LadderBoard> listCompleteLadderBoard() {
        return ladderBoardRepository.findAll();
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
        LadderBoard ladder = ladderBoardRepository.findByIdOrIfActive(name, p == null ? null : p.getUsername());
        ladder.getListPlayers().stream().sorted((p1, p2) -> p2.getPoints().intValue() - p1.getPoints().intValue()).collect(Collectors.toList());
        return ladder;
    }

    @Override
    public LadderBoard getLadderBoard(String name) {
        return ladderBoardRepository.findByName(name);
    }

    @Override
    public LadderBoard updateLadderboardColor(LadderBoard l) {
        Random rand = new Random();
        int nextInt = rand.nextInt(256*256*256);
        l.setBgColor(String.format("#%06x", nextInt));
        return ladderBoardRepository.save(l);
    }

    @Override
    public LadderBoard updateLadderBoard(LadderBoard l) {
        return ladderBoardRepository.save(l);
    }

    @Override
    public Long getGenericLadderBoardId() {
        return genericladderid;
    }

    @Override
    public void resetPassword(String name) {
        LadderBoard ladder = ladderBoardRepository.findByName(name);
        if(ladder != null && ladder.getPassword() != null){
            ladder.setPassword("");
            ladderBoardRepository.save(ladder);
        }
    }
}
