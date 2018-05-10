package quinielas.controller2;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quinielas.model.*;
import quinielas.model.form.*;
import quinielas.model.views.ViewLadderBoard;
import quinielas.model.views.ViewResumeMatches;
import quinielas.service2.*;
import quinielas.utils.ScoreMath;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private LadderboardService ladderboardService;

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
        return ViewLadderBoard.fromList(ladderboardService.listLadderBoard());
    }

    @RequestMapping(value = "/ladders", method = RequestMethod.POST)
    @ResponseBody
    public List<ViewLadderBoard> getLadders(@RequestBody TokenForm p) {
        Player player = playerService.getPlayerByToken(p.getToken());
        return ViewLadderBoard.fromList(ladderboardService.listLadderBoard(player));
    }

    @RequestMapping(value = "/ladders/detail", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard getLaddersComplete(@RequestBody GetLadderForm p) {
        Player player = playerService.getPlayerByToken(p.getToken());
        return new ViewLadderBoard(ladderboardService.getLadderBoardByName(p.getLaddername(), player));
    }

    @RequestMapping(value = "/ladders/detail", method = RequestMethod.GET)
    @ResponseBody
    public ViewLadderBoard getLaddersCompleteNoPassword(@RequestParam("laddername") String laddername) {
        LadderBoard l = ladderboardService.getLadderBoard(laddername);
        if (!isPasswordProtected(l) || !isGeneric(l)) {
             l.getListPlayers().stream().sorted((p1, p2) -> p2.getPoints().intValue() - p1.getPoints().intValue()).collect(Collectors.toList());
            return new ViewLadderBoard(l);
        }
        return null;
    }

    @RequestMapping(value = "/joinladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard joinLadder(@RequestBody JoinLadderForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderboardService.joinLadderBoard(form.getLaddername(), form.getPassword(), player));
    }


    @RequestMapping(value = "/createladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard createLadder(@RequestBody JoinLadderForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        return new ViewLadderBoard(ladderboardService.createLadderBoard(form.getLaddername(), form.getPassword(), player,true));
    }


    @RequestMapping(value = "/playermatches", method = RequestMethod.GET)
    @ResponseBody
    public List<DOMMatch> getMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderboardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if (isPasswordProtected(l) && !isGeneric(l))  return null;

        return groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId());
    }

    @RequestMapping(value = "/playermatches", method = RequestMethod.POST)
    @ResponseBody
    public List<DOMMatch> getMatches(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            if (!l.getName().equals(genericLaddername) && l.getPassword() != null && (lbp == null || !lbp.getActive()))
                return null;

            return groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId());

        }
        return null;
    }


    @RequestMapping(value = "/updateplayerstatus", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard changePlayerStatus(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbp = l.getPlayerByName(player.getUsername());
            LadderBoardPlayer lbpToUpdate = l.getPlayerByName(form.getUsername());
            if (!lbp.getAdmin() || !lbp.getActive() || lbpToUpdate == null) return null;

            lbpToUpdate.setAdmin(form.getAdmin());
            lbpToUpdate.setActive(form.getActivate());
            return new ViewLadderBoard(ladderboardService.updateLadderBoard(l));
        }
        return null;
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.GET)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestParam("username") String username, @RequestParam("laddername") String laddername, @RequestHeader HttpHeaders headers) {
        LadderBoard l = ladderboardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if ( isPasswordProtected(l) && !isGeneric(l)) return null;

        List<DOMGroup> groups2 = groupService.getGroupsByPlayerAndLadder(p.getId(), l.getId());
        List<PlayerGroup> groups = new ArrayList<>();
        groups2.stream().forEachOrdered(g-> groups.add(g.generatePlayerGroup()));
        return groups.stream().filter(x -> x!=null).collect(Collectors.toList());
    }

    @RequestMapping(value = "/playergroups", method = RequestMethod.POST)
    @ResponseBody
    public List<PlayerGroup> getGroups(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            if (!isGeneric(l) && isPasswordProtected(l) && !isActive(l,p)) return null;

            List<DOMGroup> groups2 = groupService.getGroupsByPlayerAndLadder(p.getId(), l.getId());
            List<PlayerGroup> groups = new ArrayList<>();
            groups2.stream().forEachOrdered(g-> groups.add(g.generatePlayerGroup()));

            return groups.stream().filter(x -> x!=null).collect(Collectors.toList());

        }
        return null;
    }


    @RequestMapping(value = "/updatematch", method = RequestMethod.POST)
    @ResponseBody
    public void updateMatch(@RequestBody UpdateMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        groupService.updatePlayerMatches(player, l, form.getListMatches() );
    }


    @RequestMapping(value = "/banplayer", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard banPlayer(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            LadderBoardPlayer lbpToUpdate = l.getPlayerByName(form.getUsername());
            if (!isAdmin(l,player) || !isActive(l,player) || lbpToUpdate == null) return null;
            Player p = new Player();
            p.setUsername(form.getUsername());
            return new ViewLadderBoard(ladderboardService.leaveLadderBoard(l, p));
        }
        return null;
    }

    @RequestMapping(value = "/updatecolor", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard updatecolor(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            if (!isAdmin(l,player) || !isActive(l,player)) return null;
            return new ViewLadderBoard(ladderboardService.updateLadderboardColor(l));
        }
        return null;
    }


    @RequestMapping(value = "/leaveladder", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard leaveLadder(@RequestBody PlayerStatusForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        if (l != null) {
            if (!isActive(l,player)) return null;
            return new ViewLadderBoard(ladderboardService.leaveLadderBoard(l, player));
        }
        return null;
    }

    @RequestMapping(value = "/updatemainmatch", method = RequestMethod.POST)
    @ResponseBody
    public ViewLadderBoard updateMainMatch(@RequestBody UpdateMatchForm form) {
        if (playerService.encode(form.getPassword()).equals(generalKey)) {
            LadderBoard l = ladderboardService.getLadderBoard(genericLaddername);
            Player p = playerService.getPlayerByUsername(genericUsername);
            groupService.updatePlayerMatches(p, l, form.getListMatches());
        }
        return null;
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    @ResponseBody
    public void resetpassword(@RequestBody JoinLadderForm form) {
        if (playerService.encode(form.getPassword()).equals(generalKey)) {
            ladderboardService.resetPassword(form.getLaddername());
        }
    }

    @RequestMapping(value = "/nextmatches", method = RequestMethod.GET)
    @ResponseBody
    public ViewResumeMatches getNextMatches(@RequestParam("username") String username, @RequestParam("laddername") String laddername) {
        LadderBoard l = ladderboardService.getLadderBoard(laddername);
        Player p = playerService.getPlayerByUsername(username);
        if (isPasswordProtected(l) && !isGeneric(l)) return null;
        ViewResumeMatches result =  new ViewResumeMatches();

        List<DOMMatch> matches = groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId());
        DOMMatch next =  matches.stream().filter(p2-> !p2.getFinished()).findFirst().orElse(null);
        DOMMatch prev =  matches.stream().filter(DOMMatch::getFinished).reduce((first, second) -> second).orElse(null);
        if(next != null){
            result.getNextMatches().add(next);
        }
        if(prev != null){
            result.getPrevMatches().add(prev);
        }
        return result;
    }

    @RequestMapping(value = "/nextmatches", method = RequestMethod.POST)
    @ResponseBody
    public ViewResumeMatches getNextMatchesPost(@RequestBody PlayerMatchForm form) {
        Player player = playerService.getPlayerByToken(form.getToken());
        LadderBoard l = ladderboardService.getLadderBoard(form.getLaddername());
        Player p = playerService.getPlayerByUsername(form.getUsername());
        if (l != null) {
            if (!isGeneric(l) && isPasswordProtected(l)&& !isActive(l,player)) return null;

            ViewResumeMatches result =  new ViewResumeMatches();
            List<DOMMatch> matches = groupService.getMatchesByPlayerAndLadder(p.getId(),l.getId());
            DOMMatch next =  matches.stream().filter(p2-> !p2.getFinished()).findFirst().orElse(null);
            DOMMatch prev =  matches.stream().filter(DOMMatch::getFinished).reduce((first, second) -> second).orElse(null);
            if(next != null){
                result.getNextMatches().add(next);
            }
            if(prev != null){
                result.getPrevMatches().add(prev);
            }
            return result;

        }

        return null;

    }


    private boolean isAdmin(LadderBoard l, Player p){
        return notNull(l) && notNull(p) && l.getPlayerByName(p.getUsername()).getAdmin();
    }

    private boolean isActive(LadderBoard l, Player p){
        return notNull(l) && notNull(p) && l.getPlayerByName(p.getUsername()).getActive();
    }

    private boolean isGeneric(LadderBoard l){
        return notNull(l) && l.getName().equals(genericLaddername);
    }

    private boolean isPasswordProtected(LadderBoard l){
        return notNull(l) && l.getPassword() != null;
    }

    private boolean notNull(Object o){
        return o != null;
    }
}
