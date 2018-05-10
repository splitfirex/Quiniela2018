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

    private static LadderBoard systemLadder;
    private static Player systemPlayer;

    private void getDefaults() {
        systemLadder = ladderboardService.getLadderBoard(genericLaddername);
        systemPlayer = playerService.getPlayerByUsername(genericPlayername);
    }


    public void updatesPoints() {
        if (systemLadder == null) getDefaults();
        List<LadderBoard> ladders = ladderboardService.listCompleteLadderBoard().stream().filter(lad -> !lad.getName().equals(genericLaddername)).collect(Collectors.toList());
        List<DOMMatch> systemMatches = groupService.getGroupsByPlayerAndLadder(systemPlayer.getId(), systemLadder.getId())
                .stream().map(doc -> doc.getMatches())
                .flatMap(List::stream).collect(Collectors.toList());

        ladders.parallelStream().forEach(
                lad -> {
                    lad.getListPlayers().parallelStream().forEach(
                            lbp -> {
                                lbp.setPoints(0L);
                                Player p = playerService.getPlayerByUsername(lbp.getUsername());
                                List<DOMGroup> playerGroups = groupService.getGroupsByPlayerAndLadder(p.getId(), lad.getId());
                                List<DOMMatch> playerMatches = playerGroups
                                        .stream().map(doc -> doc.getMatches())
                                        .flatMap(List::stream).collect(Collectors.toList());
                                for (int i = 0; i < playerMatches.size(); i++) {
                                    Long value = systemMatches.get(i).compareMatch(playerMatches.get(i));
                                    if (value == null) continue;
                                    playerMatches.get(i).setStatus(value.intValue());
                                    playerMatches.get(i).setFinished(true);
                                    systemMatches.get(i).setFinished(true);
                                    lbp.setPoints(lbp.getPoints() + value);
                                }
                                playerGroups.stream().forEach(g -> groupService.updateGroup(g));
                            }

                    );
                    ladderboardService.updateLadderBoard(lad);
                }
        );

        long finalizadosGrupos = systemMatches.stream().filter(f -> !f.getFinished() && f.getType().equals("group")).count();
        if (finalizadosGrupos == 0) {
            ladders.parallelStream().forEach(
                    lad -> {
                        lad.getListPlayers().parallelStream().forEach(
                                lbp -> {
                                    Player p = playerService.getPlayerByUsername(lbp.getUsername());
                                    List<DOMGroup> playerGroups = groupService.getGroupsByPlayerAndLadder(p.getId(), lad.getId());
                                    playerGroups.stream().filter(f->f.getName().equals("Round of 16")).map(doc -> doc.getMatches())
                                            .flatMap(List::stream).collect(Collectors.toList()).forEach( gg-> {
                                        DOMMatch domma = systemMatches.stream().filter(ff-> ff.getId()==gg.getId()).findFirst().get();
                                        gg.setAway_team(domma.getAway_team());
                                        gg.setHome_team(domma.getHome_team());
                                    });
                                    groupService.updateGroup(playerGroups.stream().filter(f->f.getName().equals("Round of 16")).findFirst().get());
                                }

                        );
                    }
            );
        }
    }

}
