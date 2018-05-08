package quinielas.controller2;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quinielas.model.Team;
import quinielas.service2.TeamService;
import quinielas.utils.dom.DOMTeam;

import java.util.List;

@Controller
@RequestMapping("/team")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/all")
    @ResponseBody
    public List<DOMTeam> getAllTeams() {
        return teamService.getAllTeams();
    }


}
