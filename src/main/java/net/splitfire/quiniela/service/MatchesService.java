package net.splitfire.quiniela.service;

import net.splitfire.quiniela.model.Match;

import java.util.Date;
import java.util.List;

public interface MatchesService {

    List<Match> getListMatches();

    List<Match> getListMatchesByUser(String username);

    void updateListMatchesByUser(String username, List<Match> matches);

    List<Match> getMatchesByGroup(String groupName);

    List<Match> getMatchesByDate(Date groupName);

}
