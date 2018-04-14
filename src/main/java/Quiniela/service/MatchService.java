package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Match;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;

import java.util.List;

@Service
public interface MatchService {

    List<Match> getAllMatches();

    List<Match> getMatchByGroup(String Group);

    List<Match> getMatchByTeam(String Team);

    void deletePlayerMatches(LadderBoard l, Player player);

    void createPlayerMatches(LadderBoard l, Player player);

    List<PlayerMatch> getMatchesByPlayerLadder(LadderBoard l, Player p);
}
