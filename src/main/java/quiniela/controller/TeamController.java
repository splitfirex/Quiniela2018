package quiniela.controller;

import org.springframework.web.bind.annotation.PathVariable;
import quiniela.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.service.TeamService;

import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    @ResponseBody
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/id/{team}")
    @ResponseBody
    public Team getIdTeam(@PathVariable("team") Integer idTeam) {
        return teamService.getTeamById(idTeam);
    }

    @GetMapping("/name/{team}")
    @ResponseBody
    public Team getIdTeam(@PathVariable("team") String name) {
        return teamService.getTeamByName(name);
    }

}
