package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quiniela.model.*;
import quiniela.model.enums.TypeMatch;
import quiniela.repository.LadderBoardRepository;
import quiniela.repository.MatchRepository;
import quiniela.repository.PlayerGroupRepository;
import quiniela.repository.PlayerMatchRepositoty;
import quiniela.utils.CVSParser;
import quiniela.utils.ScoreMath;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private AtomicLong counter = new AtomicLong(0);


    @Autowired
    MatchRepository matchRepository;

    @Autowired
    PlayerMatchRepositoty playerMatchRepositoty;

    @Autowired
    PlayerGroupRepository playerGroupRepositoty;

    @Autowired
    LadderBoardRepository ladderBoardRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    ScoreMath scoreMath;

    @Value("${player.username}")
    String genericUsername;

    @Value("${ladder.laddername}")
    String genericLaddername;

    final static private Integer CSV_MAX_VALUES_MATCHES = 6;

    private static List<Match> listMatches = new LinkedList<>();

    private static List<Long> prevMatches = new LinkedList<>();
    private static List<Long> nextMatches = new LinkedList<>();

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @PostConstruct
    private void init() {
        if (cleanAndBuild) {
            matchRepository.deleteAll();
            List<String> values = CVSParser.ParseMatches("matches.cvs");
            for (int i = CSV_MAX_VALUES_MATCHES; i < values.size(); i += CSV_MAX_VALUES_MATCHES) {
                Match match = new Match();

                match.setId(Long.parseLong(values.get(i)));
                ZonedDateTime zoneTime = ZonedDateTime.of(LocalDateTime.parse(values.get(i + 1), formatter), ZoneId.of("UTC+02:00"));
                match.setDate(zoneTime.toInstant().toEpochMilli());

                match.setHomeTeam(values.get(i + 2));
                match.setVisitorTeam(values.get(i + 3));
                match.setTypeMatch(TypeMatch.valueOf(values.get(i + 5)));
                match.setEditable(true);
                match.setFinish(false);
                listMatches.add(match);
            }
            matchRepository.saveAll(listMatches);
            updatePrevNextMatches();
        } else {
            listMatches = matchRepository.findAllFixtures(new Sort(Sort.Direction.ASC, "id"));
            updatePrevNextMatches();
        }
    }


    @Override
    public List<Match> getAllMatches() {
        return listMatches;
    }

    @Override
    public List<Match> getAllMatchesAndUpdate() {
        ZonedDateTime currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        List<Match> matches = getAllMatches();
        for (Match m : matches) {
            ZonedDateTime zoneTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(m.getDate()), ZoneId.of("UTC+02:00")).plusDays(-1L);
            if (currentTime.isAfter(zoneTime) && m.isEditable()) {
                m.setEditable(false);
                matchRepository.save(m);
            }
        }
        return matches;
    }

    @Override
    public void createPlayerMatches(LadderBoard l, Player player) {
        List<PlayerMatch> result = new ArrayList<>();
        for (Match match : getAllMatches()) {
            PlayerMatch pm = new PlayerMatch();
            pm.setId(counter.getAndIncrement());
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
        return playerMatchRepositoty.findAllByUserIdAndLadderboardID(p.getId(), l.getId(), new Sort(Sort.Direction.ASC, "idMatch"));
    }

    @Override
    public List<PlayerMatch> updatePlayerMatches(Player p, LadderBoard l, Long idMatch, Integer homeScore, Integer visitScore) {
        PlayerMatch pm = playerMatchRepositoty.findOneByUserIdAndLadderboardID(p.getId(), l.getId(), idMatch);
        pm.sethS(homeScore);
        pm.setvS(visitScore);
        playerMatchRepositoty.save(pm);
        List<PlayerMatch> matches = getMatchesByPlayerLadder(l, p);
        List<PlayerGroup> groups = playerGroupRepositoty.findAllByUserIdAndLadderboardID(p.getId(), l.getId());
        scoreMath.processScores(l, p, matches, groups);
        playerMatchRepositoty.saveAll(matches.stream().filter(f -> f.update).collect(Collectors.toList()));
        playerGroupRepositoty.saveAll(groups);
        ladderBoardRepository.save(l);

        if (l.getName().equals(genericLaddername) && p.getUsername().equals(genericUsername)) {
            listMatches.get(idMatch.intValue()).setFinish(true);
            matchRepository.save(listMatches.get(idMatch.intValue()));
            updatePrevNextMatches();
        }

        return matches;
    }

    private void updatePrevNextMatches() {
        Match prev = null;
        Match next = null;
        prevMatches = new LinkedList<>();
        nextMatches = new LinkedList<>();

        for (Match match : listMatches) {
            if (!match.getFinish()) {
                next = match;
                break;
            }
            prev = match;

        }

        for (Match match : listMatches) {
            if (prev != null && sameDate(prev, match) && match.getFinish()) {
                prevMatches.add(match.getId());
            }
            if (next != null && sameDate(next, match) && !match.getFinish()) {
                nextMatches.add(match.getId());
            }
        }
    }

    @Override
    public void updatePlayerMatches(List<PlayerMatch> matches) {
        playerMatchRepositoty.saveAll(matches);
    }

    @Override
    public List<PlayerMatch> getPrevMatches(LadderBoard l, Player p) {
        return playerMatchRepositoty.findAllByIds(prevMatches, p.getId(), l.getId(), new Sort(Sort.Direction.ASC, "idMatch"));
    }

    @Override
    public List<PlayerMatch> getNextMatches(LadderBoard l, Player p) {
        return playerMatchRepositoty.findAllByIds(nextMatches, p.getId(), l.getId(), new Sort(Sort.Direction.ASC, "idMatch"));
    }

    private Boolean sameDate(Match match1, Match match2) {

        ZonedDateTime zoneTime1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(match1.getDate()), ZoneId.of("UTC+02:00"));
        ZonedDateTime zoneTime2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(match2.getDate()), ZoneId.of("UTC+02:00"));

        if (zoneTime1.getDayOfYear() == zoneTime2.getDayOfYear()) {
            return true;
        }

        return false;
    }

    @Override
    public void deletePlayerMatches(LadderBoard l, Player player) {
        playerMatchRepositoty.deleteAll(playerMatchRepositoty.findAllByUserIdAndLadderboardID(player.getId(), l.getId(), new Sort(Sort.Direction.DESC, "idMatch")));
    }


}
