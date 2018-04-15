package quiniela.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.model.Group;
import quiniela.model.Match;
import quiniela.service.GroupService;
import quiniela.service.MatchService;

import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/all")
    @ResponseBody
    public List<Match> getAllGroups() {
        return matchService.getAllMatches();
    }
}