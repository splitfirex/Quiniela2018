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

    List<Match> getAllMatchesAndUpdate();

    void deletePlayerMatches(LadderBoard l, Player player);

    void createPlayerMatches(LadderBoard l, Player player);

    List<PlayerMatch> getMatchesByPlayerLadder(LadderBoard l, Player p);

    List<PlayerMatch> updatePlayerMatches(Player p, LadderBoard l, Long idMatch, Integer homeScore, Integer visitScore);

    void updatePlayerMatches(List<PlayerMatch> matches);

    List<PlayerMatch> getPrevMatches(LadderBoard l, Player p);

    List<PlayerMatch> getNextMatches(LadderBoard l, Player p);
}
