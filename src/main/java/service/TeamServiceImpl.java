package service;

import model.Group;
import model.Team;
import model.enums.TeamName;
import utils.CVSParser;

import java.util.ArrayList;
import java.util.List;

public class TeamServiceImpl implements TeamService {

    static private List<Team> listTeams = new ArrayList<>();
    static GroupService groupService = GroupService.instance;
    final static private Integer CSV_MAX_VALUES_TEAM = 4;

    static {
        List<String> values = CVSParser.ParseMatches("teams.cvs");
        for (int i = CSV_MAX_VALUES_TEAM; i < values.size(); i += CSV_MAX_VALUES_TEAM) {
            listTeams.add(new Team(Integer.parseInt(values.get(i)), values.get(i + 1), values.get(i + 2), values.get(i + 3), "URL"));
            if (groupService.getGroupByName(values.get(i + 3)) == null) {
                groupService.setGroup(new Group(values.get(i + 3)));
            }
        }
    }

    @Override
    public List<Team> getAllTeams() {
        return listTeams;
    }

    @Override
    public Team getTeamById(int idTeam) {
        return listTeams.get(idTeam);
    }

    @Override
    public Team getTeamByName(String name) {
        for (Team t : listTeams) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

    @Override
    public List<String> getTeamsByGroup(String idGroup) {
        return groupService.getGroupByName(idGroup).getTeams();
    }
}
