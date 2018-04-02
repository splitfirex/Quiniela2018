package service;

import model.Match;
import model.enums.GroupName;
import model.enums.TeamName;

import java.util.List;

public interface MatchService {

    static MatchService instance = new MatchServiceImpl();

    List<Match> getAllMatches();

    List<Match> getMatchByGroup(String Group);

    List<Match> getMatchByTeam(String Team);
}
