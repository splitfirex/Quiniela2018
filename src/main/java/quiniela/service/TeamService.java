package quiniela.service;

import quiniela.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {

    List<Team> getAllTeams();

    Team getTeamById(long idTeam);

    Team getTeamByName(String name);

    List<String> getTeamsByGroup(String idGroup);

    Long getTeamIdByName(String name);

    String getTeamNameById(Long name);

}
