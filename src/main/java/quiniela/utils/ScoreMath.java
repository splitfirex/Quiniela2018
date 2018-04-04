package quiniela.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.*;
import quiniela.service.TeamService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreMath {

    @Autowired
    TeamService teamService;

    public void processScores(Player realPlayer, Player myPlayer) {
       Integer points = 0;
       myPlayer.setGroupTeams(new HashMap<>());
        for (int i = 0; i < myPlayer.getMatchList().size(); i++) {
            PlayerMatch realMatch = realPlayer.getMatchList().get(i);
            PlayerMatch myMatch = myPlayer.getMatchList().get(i);
            if (realMatch.getvScore() != null
                    && realMatch.gethScore() != null) {
                // 3 points same score
                if (sameTeam(realMatch, myMatch) && sameScore(realMatch, myMatch)) {
                    points += 3;
                }
                //1 point same result
                if (sameTeam(realMatch, myMatch) && sameResult(realMatch, myMatch)) {
                    points += 1;
                }
            }

            if (myMatch.getvScore() != null
                    && myMatch.gethScore() != null) {
                System.out.println(myMatch.gethTeam());
                Team team = teamService.getTeamByName(myMatch.gethTeam());
                String teamGroup = team.getGroup();

                if (!myPlayer.getGroupTeams().containsKey(teamGroup)) {
                    myPlayer.getGroupTeams().put(teamGroup, new HashMap<String, TeamGroup>() {{
                        put(myMatch.gethTeam(), assignPoints(myMatch, false, new TeamGroup()));
                        put(myMatch.getvTeam(), assignPoints(myMatch, true, new TeamGroup()));
                    }});
                } else {
                    if (myPlayer.getGroupTeams().get(teamGroup).get(myMatch.gethTeam()) == null) {
                        myPlayer.getGroupTeams().get(teamGroup).put(myMatch.gethTeam(), new TeamGroup());
                    }
                    if (myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getvTeam()) == null) {
                        myPlayer.getGroupTeams().get(teamGroup).put(myMatch.getvTeam(), new TeamGroup());
                    }
                    assignPoints(myMatch, false, myPlayer.getGroupTeams().get(teamGroup).get(myMatch.gethTeam()));
                    assignPoints(myMatch, true, myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getvTeam()));

                }
            }
        }
        myPlayer.setPoints(points);
    }

    private boolean sameTeam(PlayerMatch m1, PlayerMatch m2) {
        return m1.gethTeam().equals(m2.gethTeam()) &&
                m2.getvTeam().equals(m1.getvTeam());
    }

    private boolean sameScore(PlayerMatch m1, PlayerMatch m2) {
        return m1.getvScore().equals(m2.getvScore()) &&
                m2.gethScore().equals(m1.gethScore());
    }

    private boolean sameResult(PlayerMatch m1, PlayerMatch m2) {
        return (m1.getvScore() > m1.gethScore() && m2.getvScore() > m2.gethScore())
                || (m1.getvScore() <= m1.gethScore() && m2.getvScore() <= m2.gethScore());
    }

    private TeamGroup assignPoints(PlayerMatch m1, boolean visitor, TeamGroup tg) {
        if (!visitor) {
            if (m1.gethScore() > m1.getvScore()) {
                tg.setPoints(tg.getPoints() + 3);

            }
            if (m1.gethScore() == m1.getvScore()) {
                tg.setPoints(tg.getPoints() + 1);
            }
            tg.setPositiveGoals(tg.getPositiveGoals() + m1.gethScore());
            tg.setNegativeGoals(tg.getNegativeGoals() + m1.getvScore());

        } else {
            if (m1.gethScore() < m1.getvScore()) {
                tg.setPoints(tg.getPoints() + 3);

            }
            if (m1.gethScore() == m1.getvScore()) {
                tg.setPoints(tg.getPoints() + 1);
            }
            tg.setPositiveGoals(tg.getPositiveGoals() + m1.getvScore());
            tg.setNegativeGoals(tg.getNegativeGoals() + m1.gethScore());
        }

        return tg;
    }

    private String getTeamFromGroup(Map<String, Map<String,TeamGroup>> groupTeams, String group, int position){

        return null;
    }
}
