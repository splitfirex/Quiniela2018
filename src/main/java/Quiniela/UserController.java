package Quiniela;

import model.Match;
import model.Player;
import model.Team;
import model.views.ViewPlayerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PlayerService;
import service.TeamService;
import utils.ScoreMath;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final String REAL_WORLD_SCORES = "_NOT_A_PLAYER_";

    @GetMapping("/users")
    @ResponseBody
    public List<ViewPlayerInfo> getPlayer(@RequestParam(name="name", required=false, defaultValue="") String name) {
        List<ViewPlayerInfo> result = new ArrayList<>();
        for(Player p: PlayerService.instance.getAllPlayers()){
            if(name.isEmpty() || name.equals(p.getUsername()) ) {
                result.add(new ViewPlayerInfo(p));
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

}
