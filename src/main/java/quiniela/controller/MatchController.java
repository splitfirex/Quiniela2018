package quiniela.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.Group;
import quiniela.model.Match;
import quiniela.service.GroupService;
import quiniela.service.MatchService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/all")
    @ResponseBody
    public List<Match> getAllMatches(@RequestHeader HttpHeaders headers) {
        return matchService.getAllMatchesAndUpdate();
    }
}