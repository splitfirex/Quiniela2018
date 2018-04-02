package service;

import model.Team;
import model.enums.GroupName;
import model.enums.TeamName;

import java.util.List;

public interface TeamService {

    static TeamService instance = new TeamServiceImpl();

    List<Team> getAllTeams();

    Team getTeamById(int idTeam);

    Team getTeamByName(TeamName name);

    List<String> getTeamsByGroup(String idGroup);

}
