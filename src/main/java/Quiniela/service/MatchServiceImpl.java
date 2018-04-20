package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Match;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;
import quiniela.model.enums.TypeMatch;
import quiniela.repository.MatchRepository;
import quiniela.repository.PlayerMatchRepositoty;
import quiniela.utils.CVSParser;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MatchServiceImpl implements MatchService {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private AtomicLong counter = new AtomicLong();


    @Autowired
    MatchRepository matchRepository;

    @Autowired
    PlayerMatchRepositoty playerMatchRepositoty;

    @Autowired
    TeamService teamService;

    final static private Integer CSV_MAX_VALUES_MATCHES = 6;


    @PostConstruct
    private void init() {
      /*  matchRepository.deleteAll();
        List<String> values = CVSParser.ParseMatches("matches.cvs");
        List<Match> inserMatches = new ArrayList<>();
        for (int i = CSV_MAX_VALUES_MATCHES; i < values.size(); i += CSV_MAX_VALUES_MATCHES) {
            Match match = new Match();

            match.setId(Long.parseLong(values.get(i)));
            ZonedDateTime zoneTime =ZonedDateTime.of(LocalDateTime.parse(values.get(i + 1), formatter), ZoneId.of("UTC+02:00"));
            match.setDate(zoneTime.toInstant().toEpochMilli());

            match.setHomeTeam(values.get(i + 2));
            match.setVisitorTeam(values.get(i + 3));
            match.setTypeMatch(TypeMatch.valueOf(values.get(i + 5)));
            inserMatches.add(match);
        }
        matchRepository.saveAll(inserMatches);*/
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

    @Override
    public void createPlayerMatches(LadderBoard l, Player player) {
        List<PlayerMatch> result = new ArrayList<>();
        for(Match match : getAllMatches()){
            PlayerMatch pm = new PlayerMatch();
            pm.setId(counter.incrementAndGet());
            pm.setIdMatch(match.getId());
            pm.sethT(teamService.getTeamIdByName(match.getHomeTeam()));
            pm.setvT(teamService.getTeamIdByName(match.getVisitorTeam()));
            pm.setIdLadder(l.getId());
            pm.setIdPlayer(player.getId());
            result.add(pm);
        }
        playerMatchRepositoty.saveAll(result);
    }

    @Override
    public List<PlayerMatch> getMatchesByPlayerLadder(LadderBoard l, Player p) {
        return playerMatchRepositoty.findAllByUserIdAndLadderboardID(p.getId(),l.getId());
    }

    @Override
    public List<PlayerMatch> updatePlayerMatches(Player p, LadderBoard l, Long idMatch, Integer homeScore, Integer visitScore) {
        PlayerMatch pm = playerMatchRepositoty.findOneByUserIdAndLadderboardID(p.getId(),l.getId(),idMatch);
        pm.sethS(homeScore);
        pm.setvS(visitScore);
        playerMatchRepositoty.save(pm);
        return getMatchesByPlayerLadder(l,p);
    }

    @Override
    public void deletePlayerMatches(LadderBoard l, Player player) {
        playerMatchRepositoty.deleteAll(playerMatchRepositoty.findAllByUserIdAndLadderboardID(player.getId(),l.getId()));
    }



}
