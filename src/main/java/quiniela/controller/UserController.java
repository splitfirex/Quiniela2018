package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;
import quiniela.model.enums.TypePlayerState;
import quiniela.model.form.JoinLadderForm;
import quiniela.model.form.LoginForm;
import quiniela.model.form.PlayerMatchForm;
import quiniela.model.form.TokenForm;
import quiniela.model.views.ViewLadderBoard;
import quiniela.model.views.ViewPlayerInfo;
import quiniela.model.views.ViewPlayerMatchesGroups;
import quiniela.service.LadderBoardService;
import quiniela.service.LoginService;
import quiniela.service.MatchService;
import quiniela.service.PlayerService;
import quiniela.utils.ScoreMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
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

    @RequestMapping(value = "/joinladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard joinLadder(@RequestBody JoinLadderForm form) {
        return null;
    }


    @RequestMapping(value = "/createladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard createLadder(@RequestBody JoinLadderForm form) {
        return null;
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public ViewPlayerMatchesGroups updateMatch(@RequestBody PlayerMatchForm playerMatch ) {

        return null;
    }
}
