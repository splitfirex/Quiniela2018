package quiniela.controller2;

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
import quiniela.service.LadderBoardService;
import quiniela.service2.*;
import quiniela.utils.ScoreMath;
import quiniela.utils.dom.DOMGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private LadderBoardService ladderBoardService;

    @Autowired
    private ScoreMath scoreMath;

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
        Player player = playerService.getPlayerByToken(p.getToken());
        return ViewLadderBoard.fromList(ladderBoardService.listLadderBoard(player));
    }

    @RequestMapping(value = "/ladders/detail", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard getLaddersComplete(@RequestBody GetLadderForm p) {
        Player player = playerService.getPlayerByToken(p.getToken());
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
        Player player = playerService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.joinLadderBoard(form.getLaddername(), form.getPassword(), player));
    }


    @RequestMapping(value = "/createladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard createLadder(@RequestBody JoinLadderForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderBoardService.createLadderBoard(form.getLaddername(), form.getPassword(), player));
    }


    @RequestMapping(value = "/playermatches", method = RequestMethod.GET)
    @ResponseBody
    public List<DOMGroup> getMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername))
            return null;

        return groupService.getGroupsByPlayerAndLadder(p.getId(),l.getId());
    }

    @RequestMapping(value = "/playermatches", method = RequestMethod.POST)
    @ResponseBody
    public List<DOMGroup> getMatches(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            return groupService.getGroupsByPlayerAndLadder(p.getId(),l.getId());

        }
        return null;
    }


    @RequestMapping(value = "/updateplayerstatus", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard changePlayerStatus(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
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

        List<DOMGroup> groups2 = groupService.getGroupsByPlayerAndLadder(p.getId(), l.getId());
        List<PlayerGroup> groups = new ArrayList<>();
        for(DOMGroup group : groups2){
            groups.add(group.generatePlayerGroup());
        }

        return groups;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            List<DOMGroup> groups2 = groupService.getGroupsByPlayerAndLadder(p.getId(), l.getId());
            List<PlayerGroup> groups = new ArrayList<>();
            for(DOMGroup group : groups2){
                groups.add(group.generatePlayerGroup());
            }

            return groups;

        }
        return null;
    }


    @RequestMapping(value = "/updatematch", method = RequestMethod.POST)
    @ResponseBody
    public List<DOMGroup> updateMatch(@RequestBody UpdateMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        return groupService.updatePlayerMatches(player.getId(), l.getId(), form.getIdMatch(), form.getHomeScore(), form.getVisitScore());
    }


    @RequestMapping(value = "/banplayer", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard banPlayer(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
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
        Player player = playerService.getPlayerByToken(form.getToken());
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
        Player player = playerService.getPlayerByToken(form.getToken());
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
        if (playerService.encode(form.getPassword()).equals(generalKey)) {
            LadderBoard l = ladderBoardService.getLadderBoard(genericLaddername);
            Player p = playerService.getPlayerByUsername(genericUsername);
            groupService.updatePlayerMatches(p.getId(), l.getId(), form.getIdMatch() - 1, form.getHomeScore(), form.getVisitScore());
            scoreMath.updatesPoints();

        }
        return null;
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    @ResponseBody
    public void resetpassword(@RequestBody JoinLadderForm form) {
        if (playerService.encode(form.getPassword()).equals(generalKey)) {
            ladderBoardService.resetPassword(form.getLaddername());
        }
    }

    @RequestMapping(value = "/nextmatches", method = RequestMethod.GET)
    @ResponseBody
    public ViewResumeMatches getNextMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderBoardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ((l == null || (l != null && l.getPassword() != null)) && !l.getName().equals(genericLaddername))            return null;

        ViewResumeMatches result =  new ViewResumeMatches();
       // result.getNextMatches().addAll(matchService.getNextMatches(l,p));
       // result.getPrevMatches().addAll(matchService.getPrevMatches(l,p));
        return result;
    }



    @RequestMapping(value = "/nextmatches", method = RequestMethod.POST)
    @ResponseBody
    public ViewResumeMatches getNextMatchesPost(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderBoardService.getLadderBoard(form.getLaddername());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            ViewResumeMatches result =  new ViewResumeMatches();
          //  result.getNextMatches().addAll(matchService.getNextMatches(l,p));
          //  result.getPrevMatches().addAll(matchService.getPrevMatches(l,p));
            return result;

        }

        return null;

    }
}
