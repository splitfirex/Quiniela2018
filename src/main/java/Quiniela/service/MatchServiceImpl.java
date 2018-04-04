package quiniela.service;

import quiniela.model.Match;
import quiniela.model.enums.TypeMatch;
import quiniela.utils.CVSParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MatchServiceImpl implements MatchService {

    static ArrayList<Match> ListMatches = new ArrayList<>();
   // static TeamService teamService = TeamService.instance;
    static private final AtomicInteger counter = new AtomicInteger(1);
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    final static private Integer CSV_MAX_VALUES_MATCHES = 6;

    static {

        List<String> values = CVSParser.ParseMatches("matches.cvs");
        for (int i = CSV_MAX_VALUES_MATCHES; i < values.size(); i += CSV_MAX_VALUES_MATCHES) {
            ListMatches.add(new Match(Integer.parseInt(values.get(i)),
                    LocalDateTime.parse(values.get(i + 1), formatter),
                    TypeMatch.valueOf(values.get(i + 5)))
                    .setHomeTeam(values.get(i + 2))
                    .setVisitorTeam(values.get(i + 3)));

        }
    }


    @Override
    public List<Match> getAllMatches() {
        ListMatches.sort(Comparator.comparing(Match::getDate));
        return (List<Match>) ListMatches.clone();
    }

    @Override
    public List<Match> getMatchByGroup(String group) {
    /*    List<Match> result = new ArrayList<>();
        for (String tn : teamService.getTeamsByGroup(group)) {
            for (Match m : getMatchByTeam(tn)) {
                if (!result.contains(m)) {
                    result.add(m);
                }
            }
        }
        result.sort(Comparator.comparing(Match::getDate));
        return result;
        */
    return null;
    }

    @Override
    public List<Match> getMatchByTeam(String team) {
        List<Match> result = new ArrayList<>();
        for (Match m : ListMatches) {
            if (m.getHomeTeam().equals(team) || m.getVisitorTeam().equals(team)) {
                result.add(m);
            }
        }
        result.sort(Comparator.comparing(Match::getDate));
        return result;
    }


}
