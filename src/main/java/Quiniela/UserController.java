package Quiniela;

import model.Player;
import model.Team;
import model.views.ViewPlayerInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PlayerService;
import service.TeamService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

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

}
