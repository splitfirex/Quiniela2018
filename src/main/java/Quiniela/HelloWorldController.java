package Quiniela;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import model.Match;
import model.Player;
import model.Team;
import model.enums.GroupName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.MatchService;
import service.PlayerService;
import service.TeamService;

@Controller
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public List<Player> sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return PlayerService.instance.getAllPlayers();
    }

    @GetMapping("/hello-world1")
    @ResponseBody
    public List<Match> sayHello1(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return MatchService.instance.getAllMatches();
    }

}
