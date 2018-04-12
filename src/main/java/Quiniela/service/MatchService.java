package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Match;
import quiniela.model.PlayerMatch;

import java.util.List;

@Service
public interface MatchService {

    static MatchService instance = new MatchServiceImpl();

    List<Match> getAllMatches();

    List<Match> getMatchByGroup(String Group);

    List<Match> getMatchByTeam(String Team);

    List<PlayerMatch> getPlayerMatches();
}
