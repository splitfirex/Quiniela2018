package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.PlayerMatch;
import quiniela.model.form.LoginForm;
import quiniela.model.form.PlayerMatchForm;
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


    @RequestMapping(value = "/ladders", method = RequestMethod.PUT)
    @ResponseBody
    public Boolean logout(@RequestBody LoginForm form) {


        return loginService.logout(form.getToken());
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public ViewPlayerMatchesGroups updateMatch(@RequestBody PlayerMatchForm playerMatch ) {

        Player p = loginService.getPlayerByToken(playerMatch.getToken());
        LadderBoard l =ladderBoardService.getTournamentById(playerMatch.getIdLadder());
        if(l.getListPlayers().containsKey(p.getUsername())){

            l.getLadderBoardPlayers().get(p.getId()).getListMatches().get(playerMatch.getId()).sethS(playerMatch.gethS());
            l.getLadderBoardPlayers().get(p.getId()).getListMatches().get(playerMatch.getId()).sethS(playerMatch.getvS());

            scoreMath.processScores(l, p.getId());

            ladderBoardService.updateLadderBoard(l);
        }

        return new ViewPlayerMatchesGroups(l,p.getId()).setToken(playerMatch.getToken());
    }
}
