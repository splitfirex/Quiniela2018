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
import quiniela.model.views.ViewResumeMatches;
import quiniela.service.*;
import quiniela.utils.ScoreMath;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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

    @Value("${player.username}")
    String genericUsername;

    @Value("${general.key}")
    String generalKey;

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
        if (l.getPassword() == null || l.getName().equals(genericLaddername)) {
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
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername))
            return null;

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
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

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
            return new ViewLadderBoard(ladderBoardService.updateLadderBoard(l));
        }
        return null;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestParam("username") String username, @RequestParam("laddername") String laddername, @RequestHeader HttpHeaders headers) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername))
            return null;

        List<PlayerGroup> groups = groupService.getGroupsByPlayerLadder(l, p);
        // Sorting the list based on values
        for (PlayerGroup pg : groups) {
            Collections.sort(pg.getDetails(), new Comparator<PlayerGroupDetail>() {
                @Override
                public int compare(PlayerGroupDetail d2, PlayerGroupDetail d1) {
                    if (d1.getP().compareTo(d2.getP()) != 0) {
                        return d1.getP().compareTo(d2.getP());
                    } else {
                        return ((Integer) (d1.getPg() - d1.getNg())).compareTo(d2.getPg() - d2.getNg());
                    }
                }
            });
        }
        return groups;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            List<PlayerGroup> groups = groupService.getGroupsByPlayerLadder(l, p);
            // Sorting the list based on values
            for (PlayerGroup pg : groups) {
                Collections.sort(pg.getDetails(), new Comparator<PlayerGroupDetail>() {
                    @Override
                    public int compare(PlayerGroupDetail d2, PlayerGroupDetail d1) {
                        if (d1.getP().compareTo(d2.getP()) != 0) {
                            return d1.getP().compareTo(d2.getP());
                        } else {
                            return ((Integer) (d1.getPg() - d1.getNg())).compareTo(d2.getPg() - d2.getNg());
                        }
                    }
                });
            }
            return groups;

        }
        return null;
    }


    @RequestMapping(value = "/updatematch", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerMatch> updateMatch(@RequestBody UpdateMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        return matchService.updatePlayerMatches(player, l, form.getIdMatch(), form.getHomeScore(), form.getVisitScore());
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
            Player p = new Player();
            p.setUsername(form.getUsername());
            return new ViewLadderBoard(ladderBoardService.leaveLadderBoard(l, p));
        }
        return null;
    }

    @RequestMapping(value = "/updatecolor", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard updatecolor(@RequestBody PlayerStatusForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!lbp.getAdmin() || !lbp.getActive()) return null;
            return new ViewLadderBoard(ladderBoardService.updateLadderboardColor(l));
        }
        return null;
    }


    @RequestMapping(value = "/leaveladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard leaveLadder(@RequestBody PlayerStatusForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!lbp.getActive()) return null;
            return new ViewLadderBoard(ladderBoardService.leaveLadderBoard(l, player));
        }
        return null;
    }

    @RequestMapping(value = "/updatemainmatch", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard updateMainMatch(@RequestBody MainMatchForm form) {
        if (loginService.encode(form.getPassword()).equals(generalKey)) {
            LadderBoard l = ladderBoardService.getLadderBoard(genericLaddername);
            Player p = playerService.getPlayerByUsername(genericUsername);
            matchService.updatePlayerMatches(p, l, form.getIdMatch() - 1, form.getHomeScore(), form.getVisitScore());
            scoreMath.updatesPoints();

        }
        return null;
    }

    @RequestMapping(value = "/nextmatches", method = RequestMethod.GET)
    @ResponseBody
    public ViewResumeMatches getNextMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername))            return null;

        ViewResumeMatches result =  new ViewResumeMatches();
        result.getNextMatches().addAll(matchService.getNextMatches(l,p));
        result.getPrevMatches().addAll(matchService.getPrevMatches(l,p));
        return result;
    }



    @RequestMapping(value = "/nextmatches", method = RequestMethod.POST)
    @ResponseBody
    public ViewResumeMatches getNextMatchesPost(@RequestBody PlayerMatchForm form) {
        Player player = loginService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            ViewResumeMatches result =  new ViewResumeMatches();
            result.getNextMatches().addAll(matchService.getNextMatches(l,p));
            result.getPrevMatches().addAll(matchService.getPrevMatches(l,p));
            return result;

        }

        return null;

    }
}
