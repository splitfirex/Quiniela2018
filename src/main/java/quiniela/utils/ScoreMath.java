package quiniela.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quiniela.model.*;
import quiniela.model.enums.TypeMatch;
import quiniela.service.*;

import java.util.*;

@Service
public class ScoreMath {

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @Autowired
    GroupService groupService;

    @Autowired
    LadderBoardService ladderBoardService;


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

    private String getTeamGroupID(Long idTeam) {
        return teamService.getTeamGroupById(idTeam) + "-" + idTeam;
    }

    public void updatesPoints() {
        List<LadderBoard> ladders = ladderBoardService.listLadderBoard();
        List<PlayerMatch> systemMatches = matchService.getMatchesByPlayerLadder(systemLadder, systemPlayer);
        for (LadderBoard l : ladders) {
            if(l.getName().equals(genericLaddername)) continue;
            for (LadderBoardPlayer lbp : l.getListPlayers()) {
                Player p = playerService.getPlayerByUsername(lbp.getUsername());
                List<PlayerMatch> matches = matchService.getMatchesByPlayerLadder(l, p);
                lbp.setPoints(processPlayerMatchesPoints(systemMatches, matches));
                matchService.updatePlayerMatches(matches);
            }
            ladderBoardService.updateLadderBoard(l);
        }
    }

    private Long processPlayerMatchesPoints(List<PlayerMatch> systemMatches, List<PlayerMatch> playerMatches) {
        Long result = 0L;
        for (int i = 0; i < systemMatches.size(); i++) {
            if(systemMatches.get(i).getvS() == null || systemMatches.get(i).gethS() == null) continue;
            if (systemMatches.get(i).getvT() == playerMatches.get(i).getvT()
                    || systemMatches.get(i).gethT() == playerMatches.get(i).gethT()) {

                if (systemMatches.get(i).gethS() == playerMatches.get(i).gethS() &&
                        systemMatches.get(i).getvS() == playerMatches.get(i).getvS()) {
                    playerMatches.get(i).setStatus(1L);
                    result += 3L;
                } else if (systemMatches.get(i).gethS() > systemMatches.get(i).getvS() &&
                        playerMatches.get(i).gethS() > playerMatches.get(i).getvS() ||
                        (systemMatches.get(i).gethS() < systemMatches.get(i).getvS() &&
                                playerMatches.get(i).gethS() < playerMatches.get(i).getvS())
                        ) {
                    playerMatches.get(i).setStatus(0L);
                    result += 1L;
                }else{
                    playerMatches.get(i).setStatus(-1L);
                }
            }
        }

        return result;
    }

    public void processScores(LadderBoard playerLadder, Player player, List<PlayerMatch> playerMatches, List<PlayerGroup> playerGroups) {
        List<Match> matches = matchService.getAllMatches();
        Map<String, PlayerGroupDetail> team_group = new HashMap<>();

        for (PlayerMatch playerMatch : playerMatches) {


            if (matches.get(playerMatch.getIdMatch().intValue()).getTypeMatch().equals(TypeMatch.GROUP_PHASE)) {

                if (playerMatch.gethS() == null || playerMatch.getvS() == null) continue;

                if (team_group.get(getTeamGroupID(playerMatch.gethT())) == null) {
                    team_group.put(getTeamGroupID(playerMatch.gethT()), new PlayerGroupDetail());
                }
                if (team_group.get(getTeamGroupID(playerMatch.getvT())) == null) {
                    team_group.put(getTeamGroupID(playerMatch.getvT()), new PlayerGroupDetail());
                }

                // Actualizamos el puntaje del grupo
                PlayerGroupDetail homeDetail = team_group.get(getTeamGroupID(playerMatch.gethT()));
                homeDetail.setId(playerMatch.gethT());
                homeDetail.setNg(homeDetail.getNg() + playerMatch.getvS());
                homeDetail.setPg(homeDetail.getPg() + playerMatch.gethS());
                homeDetail.setP(homeDetail.getP() + (playerMatch.gethS() > playerMatch.getvS() ? 3 : playerMatch.gethS() < playerMatch.getvS() ? 0 : 1));

                PlayerGroupDetail visitDetail = team_group.get(getTeamGroupID(playerMatch.getvT()));
                visitDetail.setId(playerMatch.getvT());
                visitDetail.setNg(visitDetail.getNg() + playerMatch.gethS());
                visitDetail.setPg(visitDetail.getPg() + playerMatch.getvS());
                visitDetail.setP(visitDetail.getP() + (playerMatch.gethS() < playerMatch.getvS() ? 3 : playerMatch.gethS() > playerMatch.getvS() ? 0 : 1));
            } else if (matches.get(playerMatch.getIdMatch().intValue()).getTypeMatch().equals(TypeMatch.ROUND_OF_16)) {
                // Actualizamos los equipos del partido
                String homeGN = matches.get(playerMatch.getIdMatch().intValue()).getHomeTeam().split("_")[0];
                String homeGP = matches.get(playerMatch.getIdMatch().intValue()).getHomeTeam().split("_")[1];

                String visitGN = matches.get(playerMatch.getIdMatch().intValue()).getVisitorTeam().split("_")[0];
                String visitGP = matches.get(playerMatch.getIdMatch().intValue()).getVisitorTeam().split("_")[1];

                playerMatch.sethT(getTeamByPositionGroup(team_group, homeGN, Integer.parseInt(homeGP)));
                playerMatch.setvT(getTeamByPositionGroup(team_group, visitGN, Integer.parseInt(visitGP)));
            } else {
                String statusTeamHome = matches.get(playerMatch.getIdMatch().intValue()).getHomeTeam().split("_")[0];
                String matchTeamHome = matches.get(playerMatch.getIdMatch().intValue()).getHomeTeam().split("_")[1];

                String statusTeamVisit = matches.get(playerMatch.getIdMatch().intValue()).getVisitorTeam().split("_")[0];
                String matchTeamVisit = matches.get(playerMatch.getIdMatch().intValue()).getVisitorTeam().split("_")[1];

                playerMatch.sethT(getTeamByStatusMatch(playerMatches, statusTeamHome, Integer.parseInt(matchTeamHome) - 1));
                playerMatch.setvT(getTeamByStatusMatch(playerMatches, statusTeamVisit, Integer.parseInt(matchTeamVisit) - 1));

                if (playerMatch.getIdMatch().intValue() == playerMatches.size()) {
                    playerLadder.getPlayerByName(player.getUsername()).setWinnerTeam(playerMatch.gethS() > playerMatch.getvS() ? playerMatch.gethT() : playerMatch.getvT());
                }
            }
        }

        //Actualizamos los grupos
        for (PlayerGroup pg : playerGroups) {
            for (PlayerGroupDetail team : pg.getDetails()) {
                if (team_group.containsKey(pg.getIdGroup().toString() + "-" + team.getId())) {
                    team.setP(team_group.get(pg.getIdGroup().toString() + "-" + team.getId()).getP());
                    team.setNg(team_group.get(pg.getIdGroup().toString() + "-" + team.getId()).getNg());
                    team.setPg(team_group.get(pg.getIdGroup().toString() + "-" + team.getId()).getPg());
                }
            }
        }

    }

    private Long getTeamByPositionGroup(Map<String, PlayerGroupDetail> team_group, String group, Integer position) {
        Long keygroup = teamService.getGroupIdByName(group);
        List<PlayerGroupDetail> comparableList = new ArrayList<>();
        for (String keys : team_group.keySet()) {
            if (keys.contains(keygroup.toString() + "-")) {
                comparableList.add(team_group.get(keys));
            }
        }
        if (comparableList.isEmpty()) return null;
        Collections.sort(comparableList, new Comparator<PlayerGroupDetail>() {
            @Override
            public int compare(PlayerGroupDetail d2, PlayerGroupDetail d1) {
                if (d1.getP().compareTo(d2.getP()) != 0) {
                    return d1.getP().compareTo(d2.getP());
                } else {
                    return ((Integer) (d1.getPg() - d1.getNg())).compareTo(d2.getPg() - d2.getNg());
                }
            }
        });
        return comparableList.get(position.intValue() - 1).getId();
    }

    private Long getTeamByStatusMatch(List<PlayerMatch> playerMatches, String status, Integer matchIndex) {
        if (playerMatches.get(matchIndex).gethS() == null || playerMatches.get(matchIndex).getvS() == null) return null;
        if (status.equals("WIN")) {
            return playerMatches.get(matchIndex).gethS() < playerMatches.get(matchIndex).getvS() ? playerMatches.get(matchIndex).getvT() : playerMatches.get(matchIndex).gethT();
        } else {
            return playerMatches.get(matchIndex).gethS() > playerMatches.get(matchIndex).getvS() ? playerMatches.get(matchIndex).getvT() : playerMatches.get(matchIndex).gethT();
        }
    }

    private boolean sameTeam(Match m1, PlayerMatch m2) {
        return teamService.getTeamIdByName(m1.getHomeTeam()).equals(m2.gethT()) &&
                m2.getvT().equals(teamService.getTeamIdByName(m1.getVisitorTeam()));
    }

    private boolean sameScore(Match m1, PlayerMatch m2) {
        return m1.getScoreVisitorTeam().equals(m2.getvS()) &&
                m2.gethS().equals(m1.getScoreHomeTeam());
    }

    private boolean sameResult(Match m1, PlayerMatch m2) {
        return (m1.getScoreVisitorTeam() > m1.getScoreHomeTeam() && m2.getvS() > m2.gethS())
                || (m1.getScoreVisitorTeam() < m1.getScoreHomeTeam() && m2.getvS() < m2.gethS());
    }

    /*  private TeamGroupDetails assignPoints(PlayerMatch m1, boolean visitor, TeamGroupDetails tg) {
          if (!visitor) {
              if (m1.gethS() > m1.getvS()) {
                  tg.setP(tg.getP() + 3);

              }
              if (m1.gethS() == m1.getvS()) {
                  tg.setP(tg.getP() + 1);
              }
              tg.setPg(tg.getPg() + m1.gethS());
              tg.setNg(tg.getNg() + m1.getvS());

          } else {
              if (m1.gethS() < m1.getvS()) {
                  tg.setP(tg.getP() + 3);

              }
              if (m1.gethS() == m1.getvS()) {
                  tg.setP(tg.getP() + 1);
              }
              tg.setPg(tg.getPg() + m1.getvS());
              tg.setNg(tg.getNg() + m1.gethS());
          }

          return tg;
      }
  */
    private Long getResultOfMatch(List<PlayerMatch> matches, String winOrLose, Long idMatch) {
        PlayerMatch match = matches.get(idMatch.intValue());
        if (match.gethS() > match.getvT() && winOrLose.contains("WIN")) {
            return match.gethT();
        } else {

            return match.getvT();
        }
    }

    private Long getPositionOfGroup(HashMap<Long, PlayerGroup> teamGroup, String position) {
        Long idGroup = groupService.getGroupByName(position.split("_")[0]).getId();

        return null;
    }

}
