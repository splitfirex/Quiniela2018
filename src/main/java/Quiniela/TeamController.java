package Quiniela;

import model.Player;
import model.Team;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PlayerService;
import service.TeamService;

import java.util.List;

@Controller
public class TeamController {

    @GetMapping("/teams/all")
    @ResponseBody
    public List<Team> getAllTeams() {
        return TeamService.instance.getAllTeams();
    }

}
