package net.splitfire.quiniela.service;

import net.splitfire.quiniela.model.Match;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("matchesService")
@Transactional
public class MatchesServiceImpl implements MatchesService {

    static Map<String,Map<Integer,Match>> matchesUser = new HashMap<>();

    @Override
    public List<Match> getListMatches() {
        return null;
    }

    @Override
    public List<Match> getListMatchesByUser(String username) {
        return null;
    }

    @Override
    public void updateListMatchesByUser(String username, List<Match> matches) {

    }

    @Override
    public List<Match> getMatchesByGroup(String groupName) {
        return null;
    }

    @Override
    public List<Match> getMatchesByDate(Date groupName) {
        return null;
    }

    public static void testData(){
    }
}
