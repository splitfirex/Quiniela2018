package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.model.Player;
import quiniela.model.views.ViewPlayerInfo;
import quiniela.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final String REAL_WORLD_SCORES = "_NOT_A_PLAYER_";

    @Autowired
    private PlayerService playerService;

    @GetMapping("/id/{username}")
    @ResponseBody
    public ViewPlayerInfo getPlayer(@PathVariable("username" ) String username) {
        return new ViewPlayerInfo(playerService.getPlayerByUsername(username));
    }

    @GetMapping("/all")
    @ResponseBody
    public List<ViewPlayerInfo> getPlayers() {
        List<ViewPlayerInfo> result = new ArrayList<>();
        for(Player p : playerService.getAllPlayers()){
            result.add(new ViewPlayerInfo(p));
        }
        return result;
    }
/*
    @GetMapping("/users/matches")
    @ResponseBody
    public List<ViewPlayerMatchesGroups> getPlayerMatches(@RequestParam(name="username", required=false, defaultValue="") String name) {
        List<ViewPlayerMatchesGroups> result = new ArrayList<>();
        for(Player p: PlayerService.instance.getAllPlayers()){
            if(name.isEmpty() || name.equals(p.getUsername()) ) {
                result.add(new ViewPlayerMatchesGroups(p));
            }
        }
        return result;
    }

    @GetMapping("/users/update")
    @ResponseBody
    public int updateMatch(@RequestParam(name="username", required=true) String username,
                                            @RequestParam(name="password", required=true) String password,
                                            @RequestParam(name="idMatch", required=true) String idMatch,
                                            @RequestParam(name="homeScore", required=true) String homeScore,
                                            @RequestParam(name="visitScore", required=true) String visitScore) {
        if(username == null || password == null) return -1;
        Player p = PlayerService.instance.validateUsername(username,password);
        p.getMatchList().get(Integer.parseInt(idMatch))
                .setScoreHomeTeam(Integer.parseInt(homeScore))
                .setScoreVisitorTeam(Integer.parseInt(visitScore));
        ScoreMath.processScores(PlayerService.instance.getPlayerByUsername(REAL_WORLD_SCORES),p);
        return 0;

    }
*/
}
