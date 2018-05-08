package quinielas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quinielas.model.*;
import quinielas.model.enums.TypeMatch;
import quinielas.service2.GroupService;
import quinielas.service2.LadderboardService;
import quinielas.service2.PlayerService;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreMath {


    @Autowired
    PlayerService playerService;


    @Autowired
    GroupService groupService;

    @Autowired
    LadderboardService ladderboardService;


    @Value("${ladder.laddername}")
    String genericLaddername;

    @Value("${player.username}")
    String genericPlayername;

    private final LadderBoard systemLadder;
    private final Player systemPlayer;

    {
        systemLadder = new LadderBoard();
        systemLadder.setId(1L);
        systemLadder.setName(genericLaddername);

        systemPlayer = new Player();
        systemPlayer.setId(1L);
        systemPlayer.setUsername(genericPlayername);
    }


    public void updatesPoints() {
        List<LadderBoard> ladders = ladderboardService.listLadderBoard().stream().filter(lad -> !lad.getName().equals(genericLaddername)).collect(Collectors.toList());
        List<DOMMatch> systemMatches = groupService.getGroupsByPlayerAndLadder(systemPlayer.getId(), systemLadder.getId())
                .stream().map(doc -> doc.getMatches())
                .flatMap(List::stream).collect(Collectors.toList());

        ladders.stream().forEach(
                lad -> lad.getListPlayers().stream().forEach(
                        lbp -> {
                            lbp.setPoints(0L);
                            Player p = playerService.getPlayerByUsername(lbp.getUsername());
                            List<DOMMatch> playerMatches = groupService.getGroupsByPlayerAndLadder(p.getId(), lad.getId())
                                    .stream().map(doc -> doc.getMatches())
                                    .flatMap(List::stream).collect(Collectors.toList());
                            for(int i =0 ; i< playerMatches.size() ; i++){
                                lbp.setPoints(lbp.getPoints() + systemMatches.get(i).compareMatch(playerMatches.get(i)));
                            }
                        }
                )
        );
    }


}
