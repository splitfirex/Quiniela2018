package quinielas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quinielas.model.*;
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
                .flatMap(List::stream).sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());

        ladders.parallelStream().forEach(
                lad -> {
                    lad.getListPlayers().parallelStream().forEach(
                            lbp -> {
                                lbp.setPoints(0L);
                                Player p = playerService.getPlayerByUsername(lbp.getUsername());
                                List<DOMGroup> playerGroups = groupService.getGroupsByPlayerAndLadder(p.getId(), lad.getId());
                                List<DOMMatch> playerMatches = playerGroups
                                        .stream().map(doc -> doc.getMatches())
                                        .flatMap(List::stream)
                                        .sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate()))
                                        .collect(Collectors.toList());
                                for (int i = 0; i < playerMatches.size(); i++) {
                                    Long value = systemMatches.get(i).compareMatch(playerMatches.get(i));
                                    if (value == null){
                                        playerMatches.get(i).setStatus(null);
                                        playerMatches.get(i).setFinished(false);
                                        systemMatches.get(i).setFinished(false);
                                        continue;
                                    }
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
            List<DOMGroup> groups = groupService.getAllUnforcedGroups();

            groups.stream().filter(p -> p.getIdLadder() != ladderboardService.getGenericLadderBoardId()).forEach(f -> {
                f.getMatches().stream().forEach(m -> {
                    if (m.getType().equals("qualified")) {
                        DOMMatch systemM = systemMatches.stream().filter(ff -> ff.getId() == m.getId()).findFirst().get();
                        if (m.getHome_team() != systemM.getHome_team()) {
                            m.setHome_team(systemM.getHome_team());
                            m.setHome_result(null);
                            m.setHome_penalty(null);
                        }
                        if (m.getHome_penalty() != systemM.getAway_penalty()) {
                            m.setAway_team(systemM.getAway_team());
                            m.setAway_result(null);
                            m.setAway_penalty(null);
                        }
                    } else if (m.getType().equals("winner") || m.getType().equals("loser")) {
                        m.setHome_team(null);
                        m.setHome_result(null);
                        m.setHome_penalty(null);

                        m.setAway_team(null);
                        m.setAway_result(null);
                        m.setAway_penalty(null);
                    }
                });
            });

            groupService.updateGroup(groups);

        }
    }

}
