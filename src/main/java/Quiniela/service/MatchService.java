package quiniela.service;

import quiniela.model.Match;

import java.util.List;

public interface MatchService {

    static MatchService instance = new MatchServiceImpl();

    List<Match> getAllMatches();

    List<Match> getMatchByGroup(String Group);

    List<Match> getMatchByTeam(String Team);
}
