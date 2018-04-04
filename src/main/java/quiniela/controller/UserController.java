package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.Match;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;
import quiniela.model.PlayerMatchUpdate;
import quiniela.model.views.ViewPlayerInfo;
import quiniela.model.views.ViewPlayerMatchesGroups;
import quiniela.service.PlayerService;
import quiniela.utils.ScoreMath;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

@Controller
@RequestMapping("/user")
@ComponentScan({"quiniela.service"})
public class UserController {

    private static final String REAL_WORLD_SCORES = "_NOT_A_PLAYER_";

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScoreMath scoreMath;

    @RequestMapping(value = "/id/{username}")
    @ResponseBody
    public ViewPlayerInfo getPlayer(@PathVariable("username") String username) {
        return new ViewPlayerInfo(playerService.getPlayerByUsername(username));
    }

    @RequestMapping(value = "/all")
    @ResponseBody
    public List<ViewPlayerInfo> getPlayers() {
        List<ViewPlayerInfo> result = new ArrayList<>();
        for (Player p : playerService.getAllPlayers()) {
            result.add(new ViewPlayerInfo(p));
        }
        return result;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public ViewPlayerInfo newPlayer(@RequestBody Player player) {
        return new ViewPlayerInfo(playerService.createPlayer(player.getUsername(), player.getPassword()));
    }


    @RequestMapping(value = "/matches/{username}")
    @ResponseBody
    public List<ViewPlayerMatchesGroups> getPlayerMatches(@PathVariable(name = "username") String username) {
        List<ViewPlayerMatchesGroups> result = new ArrayList<>();
        for (Player p : playerService.getAllPlayers()) {
            if (username.isEmpty() || username.equals(p.getUsername())) {
                result.add(new ViewPlayerMatchesGroups(p));
            }
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public ViewPlayerMatchesGroups updateMatch(@RequestBody PlayerMatchUpdate playerMatch ) {
        if (playerMatch.getUsername() == null || playerMatch.getPassword() == null) return null;
        Player p = playerService.validateUsername(playerMatch.getUsername(), playerMatch.getPassword());
        for(PlayerMatch pm : p.getMatchList()){
            if(pm.getId() == playerMatch.getId()){
                pm.sethScore(Long.valueOf(playerMatch.gethScore()));
                pm.setvScore(Long.valueOf(playerMatch.getvScore()));
                break;
            }
        }
        scoreMath.processScores(playerService.getPlayerByUsername("_NOT_A_PLAYER_"), p);
        playerService.updatePlayer(p);
        return new ViewPlayerMatchesGroups(p);
    }
}
