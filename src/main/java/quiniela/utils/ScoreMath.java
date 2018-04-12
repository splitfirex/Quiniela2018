package quiniela.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.*;
import quiniela.service.MatchService;
import quiniela.service.TeamService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreMath {

    @Autowired
    TeamService teamService;

    @Autowired
    MatchService matchService;

    public void processScores(LadderBoard l, Long playerId) {
        List<Match> matches = matchService.getAllMatches();
        HashMap<Long,PlayerMatch> myMatches = l.getLadderBoardPlayers().get(playerId).getListMatches();

        int points = 0;
       for(Match match:  matches){
           if(match.getScoreHomeTeam() == null || match.getScoreVisitorTeam() == null ) continue;
           PlayerMatch myMatch = myMatches.get(match.getId());
           if(myMatch == null) continue;


           if (match.getScoreVisitorTeam() != null
                   && myMatch.gethS() != null) {
               // 3 points same score
               if (sameTeam(match, myMatch) && sameScore(match, myMatch)) {
                   points += 3;
               }
               //1 point same result
               if (sameTeam(match, myMatch) && sameResult(match, myMatch)) {
                   points += 1;
               }
           }

           //Reasign team to matches

       }

       l.getLadderBoardPlayers().get(playerId).setPoints(points);

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

    private TeamGroup assignPoints(PlayerMatch m1, boolean visitor, TeamGroup tg) {
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

    private String getTeamFromGroup(Map<String, Map<String,TeamGroup>> groupTeams, String group, int position){

        return null;
    }
}
