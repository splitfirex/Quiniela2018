package quiniela.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.*;
import quiniela.model.form.*;
import quiniela.model.views.ViewLadderBoard;
import quiniela.service.*;
import quiniela.utils.ScoreMath;

import java.util.List;


@Controller
@RequestMapping("/user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private LadderBoardService ladderBoardService;

    @Autowired
    private ScoreMath scoreMath;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private GroupService groupService;

    @Value("${ladder.laddername}")
    String genericLaddername;

    @RequestMapping(value = "/ladders", method = RequestMethod.GET)
    @ResponseBody
    public List<ViewLadderBoard> getLadders() {
        return ViewLadderBoard.fromList(ladderBoardService.listLadderBoard());
    }

    @RequestMapping(value = "/ladders", method = RequestMethod.POST)
    @ResponseBody
    public List<ViewLadderBoard> getLadders(@RequestBody TokenForm p) {
        Player player = loginService.getPlayerByToken(p.getToken());
        return ViewLadderBoard.fromList(ladderBoardService.listLadderBoard(player));
    }

    @RequestMapping(value = "/ladders/detail", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard getLaddersComplete(@RequestBody GetLadderForm p) {
        Player player = loginService.getPlayerByToken(p.getToken());
        return new ViewLadderBoard(ladderBoardService.getLadderBoardByName(p.getLaddername(), player));
    }

    @RequestMapping(value = "/ladders/detail", method = RequestMethod.GET)
    @ResponseBody
    public ViewLadderBoard getLaddersCompleteNoPassword(@RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        if(l.getPassword() == null || l.getName().equals(genericLaddername)){
            return new ViewLadderBoard(l);
        }
        return null;
    }

    @RequestMapping(value = "/joinladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard joinLadder(@RequestBody JoinLadderForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.joinLadderBoard(form.getLaddername(), form.getPassword(), player));
    }


    @RequestMapping(value = "/createladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard createLadder(@RequestBody JoinLadderForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.createLadderBoard(form.getLaddername(), form.getPassword(), player));
    }


    @RequestMapping(value = "/playermatches", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerMatch> getMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername)) return null;

        return matchService.getMatchesByPlayerLadder(l, p);
    }

    @RequestMapping(value = "/playermatches", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerMatch> getMatches(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive())) return null;

            return matchService.getMatchesByPlayerLadder(l, p);

        }
        return null;
    }


    @RequestMapping(value = "/updateplayerstatus", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard changePlayerStatus(@RequestBody PlayerStatusForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            LadderBoardPlayer lbpToUpdate = l.getPlayerByName(form.getUsername());
            if (!lbp.getAdmin() || !lbp.getActive() || lbpToUpdate == null) return null;

            lbpToUpdate.setAdmin(form.getAdmin());
            lbpToUpdate.setActive(form.getActivate());
            return new ViewLadderBoard(ladderBoardService.updateUserStatus(l,lbpToUpdate));
        }
        return null;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestParam("username") String username, @RequestParam("laddername") String laddername, @RequestHeader HttpHeaders headers) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername)) return null;

        return groupService.getGroupsByPlayerLadder(l, p);
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive())) return null;

            return groupService.getGroupsByPlayerLadder(l, p);

        }
        return null;
    }


    @RequestMapping(value = "/updatematch", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerMatch> updateMatch(@RequestBody UpdateMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        return matchService.updatePlayerMatches(player,l,form.getIdMatch(),form.getHomeScore(),form.getVisitScore());
    }


    @RequestMapping(value = "/banplayer", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard banPlayer(@RequestBody PlayerStatusForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            LadderBoardPlayer lbpToUpdate = l.getPlayerByName(form.getUsername());
            if (!lbp.getAdmin() || !lbp.getActive() || lbpToUpdate == null) return null;

            lbpToUpdate.setAdmin(form.getAdmin());
            lbpToUpdate.setActive(form.getActivate());
            Player p = new Player();
            p.setUsername(form.getUsername());
            return new ViewLadderBoard(ladderBoardService.leaveLadderBoard(l,p));
        }
        return null;
    }

}
