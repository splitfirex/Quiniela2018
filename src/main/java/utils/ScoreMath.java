package utils;

import model.Match;
import model.Player;
import model.TeamGroup;
import service.TeamService;

import java.util.HashMap;
import java.util.Map;

public class ScoreMath {

    public static void processScores(Player realPlayer, Player myPlayer) {
        Integer points = 0;
        for (int i = 0; i < myPlayer.getMatchList().size(); i++) {
            Match realMatch = realPlayer.getMatchList().get(i);
            Match myMatch = myPlayer.getMatchList().get(i);
            if (realMatch.getScoreVisitorTeam() != null
                    && realMatch.getScoreHomeTeam() != null) {
                // 3 points same score
                if (sameTeam(realMatch, myMatch) && sameScore(realMatch, myMatch)) {
                    points += 3;
                }
                //1 point same result
                if (sameTeam(realMatch, myMatch) && sameResult(realMatch, myMatch)) {
                    points += 1;
                }
            }

            if (myMatch.getScoreVisitorTeam() != null
                    && myMatch.getScoreHomeTeam() != null) {
                String teamGroup = TeamService.instance.getTeamByName(myMatch.getHomeTeam()).getGroup();
                if (myPlayer.getGroupTeams().containsKey(teamGroup)) {
                    myPlayer.getGroupTeams().put(teamGroup, new HashMap<String, TeamGroup>() {{
                        put(myMatch.getHomeTeam(), assignPoints(myMatch, false, new TeamGroup()));
                        put(myMatch.getVisitorTeam(), assignPoints(myMatch, true, new TeamGroup()));
                    }});
                } else {
                    if (myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getHomeTeam()) == null) {
                        myPlayer.getGroupTeams().get(teamGroup).put(myMatch.getHomeTeam(), new TeamGroup());
                    }
                    if (myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getVisitorTeam()) == null) {
                        myPlayer.getGroupTeams().get(teamGroup).put(myMatch.getVisitorTeam(), new TeamGroup());
                    }
                    assignPoints(myMatch, false, myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getHomeTeam()));
                    assignPoints(myMatch, true, myPlayer.getGroupTeams().get(teamGroup).get(myMatch.getVisitorTeam()));

                }
            }
        }

        myPlayer.setPoints(points);

    }

    public static boolean sameTeam(Match m1, Match m2) {
        return m1.getHomeTeam().equals(m2.getHomeTeam()) &&
                m2.getVisitorTeam().equals(m1.getVisitorTeam());
    }

    public static boolean sameScore(Match m1, Match m2) {
        return m1.getScoreVisitorTeam().equals(m2.getScoreVisitorTeam()) &&
                m2.getScoreHomeTeam().equals(m1.getScoreHomeTeam());
    }

    public static boolean sameResult(Match m1, Match m2) {
        return (m1.getScoreVisitorTeam() > m1.getScoreHomeTeam() && m2.getScoreVisitorTeam() > m2.getScoreHomeTeam())
                || (m1.getScoreVisitorTeam() <= m1.getScoreHomeTeam() && m2.getScoreVisitorTeam() <= m2.getScoreHomeTeam());
    }

    public static TeamGroup assignPoints(Match m1, boolean visitor, TeamGroup tg) {
        if (!visitor) {
            if (m1.getScoreHomeTeam() > m1.getScoreVisitorTeam()) {
                tg.setPoints(tg.getPoints() + 3);

            }
            if (m1.getScoreHomeTeam() == m1.getScoreVisitorTeam()) {
                tg.setPoints(tg.getPoints() + 1);
            }
            tg.setPositiveGoals(tg.getPositiveGoals() + m1.getScoreHomeTeam());
            tg.setNegativeGoals(tg.getNegativeGoals() + m1.getScoreVisitorTeam());

        } else {
            if (m1.getScoreHomeTeam() < m1.getScoreVisitorTeam()) {
                tg.setPoints(tg.getPoints() + 3);

            }
            if (m1.getScoreHomeTeam() == m1.getScoreVisitorTeam()) {
                tg.setPoints(tg.getPoints() + 1);
            }
            tg.setPositiveGoals(tg.getPositiveGoals() + m1.getScoreVisitorTeam());
            tg.setNegativeGoals(tg.getNegativeGoals() + m1.getScoreHomeTeam());
        }

        return tg;
    }

    public static String getTeamFromGroup(Map<String, Map<String,TeamGroup>> groupTeams, String group, int position){

        return null;
    }
}
