package quiniela.service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import quiniela.model.Group;
import quiniela.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import quiniela.repository.GroupRepository;
import quiniela.repository.TeamRepository;
import quiniela.utils.CVSParser;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    static private List<Team> listTeams = new ArrayList<>();

    final static private Integer CSV_MAX_VALUES_TEAM = 4;

    @Autowired
    static private TeamRepository teamRepository;

    @Autowired
    static private GroupRepository groupRepository;

    static {
        List<String> values = CVSParser.ParseMatches("teams.cvs");
        for (int i = CSV_MAX_VALUES_TEAM; i < values.size(); i += CSV_MAX_VALUES_TEAM) {
            teamRepository.save(new Team(Integer.parseInt(values.get(i)), values.get(i + 1), values.get(i + 2), values.get(i + 3), "URL"));

            if (groupRepository.findByName(values.get(i + 3)) == null) {
                groupRepository.save(new Group(values.get(i + 3)));
            }
        }
    }


    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(long idTeam) {
        return teamRepository.findById(idTeam);
    }

    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<String> getTeamsByGroup(String idGroup) {
        return groupRepository.findByName(idGroup).getTeams();
    }
}
