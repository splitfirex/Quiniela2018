package quiniela.controller2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.model.Team;
import quiniela.service2.TeamService;
import quiniela.utils.dom.DOMTeam;

import java.util.List;

@Controller
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    @ResponseBody
    public List<DOMTeam> getAllTeams() {
        return teamService.getAllTeams();
    }


}
