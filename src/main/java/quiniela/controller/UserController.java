package quiniela.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.*;
import quiniela.model.form.GetLadderForm;
import quiniela.model.form.JoinLadderForm;
import quiniela.model.form.PlayerMatchForm;
import quiniela.model.form.TokenForm;
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
        return new ViewLadderBoard(ladderBoardService.getLadderBoardByName(p.getLadderName(), player));
    }

    @RequestMapping(value = "/joinladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard joinLadder(@RequestBody JoinLadderForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.joinLadderBoard(form.getNameladder(), form.getPassword(), player));
    }


    @RequestMapping(value = "/createladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard createLadder(@RequestBody JoinLadderForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.createLadderBoard(form.getNameladder(), form.getPassword(), player));
    }


    @RequestMapping(value = "/playermatches", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerMatch> getMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if (l == null || (l != null && l.getPassword() != null)) return null;

        return matchService.getMatchesByPlayerLadder(l, p);
    }

    @RequestMapping(value = "/playermatches", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerMatch> getMatches(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLadder());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (l.getPassword() != null && (lbp == null || !lbp.getActive())) return null;

            return matchService.getMatchesByPlayerLadder(l, p);

        }
        return null;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if (l == null || (l != null && l.getPassword() != null)) return null;

        return groupService.getGroupsByPlayerLadder(l, p);
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLadder());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (l.getPassword() != null && (lbp == null || !lbp.getActive())) return null;

            return groupService.getGroupsByPlayerLadder(l, p);

        }
        return null;
    }

}
