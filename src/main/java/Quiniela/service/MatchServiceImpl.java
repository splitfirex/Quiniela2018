package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import quiniela.model.Match;
import quiniela.model.enums.TypeMatch;
import quiniela.repository.MatchRepository;
import quiniela.utils.CVSParser;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MatchServiceImpl implements MatchService {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamService teamService;

    final static private Integer CSV_MAX_VALUES_MATCHES = 6;


    @PostConstruct
    private void init() {
        matchRepository.deleteAll();
        List<String> values = CVSParser.ParseMatches("matches.cvs");
        List<Match> inserMatches = new ArrayList<>();
        for (int i = CSV_MAX_VALUES_MATCHES; i < values.size(); i += CSV_MAX_VALUES_MATCHES) {
            Match match = new Match();
            match.setId(Long.parseLong(values.get(i)));
            match.setDate(LocalDateTime.parse(values.get(i + 1), formatter));
            match.setHomeTeam(values.get(i + 2));
            match.setVisitorTeam(values.get(i + 3));
            match.setTypeMatch(TypeMatch.valueOf(values.get(i + 5)));
            inserMatches.add(match);
        }
        matchRepository.saveAll(inserMatches);
    }


    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAllFixtures(new Sort(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Match> getMatchByGroup(String group) {
        List<Match> result = new ArrayList<>();
        for (String tn : teamService.getTeamsByGroup(group)) {
            for (Match m : getMatchByTeam(tn)) {
                if (!result.contains(m)) {
                    result.add(m);
                }
            }
        }
        result.sort(Comparator.comparing(Match::getDate));
        return result;
    }

    @Override
    public List<Match> getMatchByTeam(String team) {
        List<Match> result = new ArrayList<>();
        for (Match m : matchRepository.findAllFixtures(new Sort(Sort.Direction.ASC, "id"))) {
            if (m.getHomeTeam().equals(team) || m.getVisitorTeam().equals(team)) {
                result.add(m);
            }
        }
        result.sort(Comparator.comparing(Match::getDate));
        return result;
    }


}
