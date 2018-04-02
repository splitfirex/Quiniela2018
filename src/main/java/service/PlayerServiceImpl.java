package service;

import model.Match;
import model.Player;
import model.enums.TeamName;
import model.views.TeamGroup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class PlayerServiceImpl implements PlayerService {

    static private Map<String, Player> playerList = new HashMap<>();
    private static MessageDigest md;
    private static GroupService groupService = GroupService.instance;
    private static MatchService matchService = MatchService.instance;

    static {
        Player p =  new Player("splitfire", "splitfire");
        p.setMatchList(matchService.getAllMatches());
        playerList.put("splitfire",p);
        p.getMatchList().get(1).setScoreHomeTeam(100);

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Player validateUsername(String userName, String password){

        Player p = playerList.get(userName);
        if (p.getUsername().equals(userName) &&
                md.digest(password.getBytes()).equals(p.getPassword().getBytes())) {
            return p;
        }

        return null;
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return  playerList.get(username);
    }

    private void cascadeUpdateMatchs(Player p){

     /*   for(Set<List<TeamGroup>> group : p.getGroupTeams().entrySet()){
            if(){

            }
        }

        for(Match m: p.getMatchList()){
            if(){

            }
        }*/

    }


    @Override
    public List<Match> updatePlayerMatch(String userName, String password, Match updatedMatch) {
        List<Match> result =new ArrayList<>();
        Player player = validateUsername(userName,password);
        if(player == null) return result;

        player.getMatchList().set(updatedMatch.getIdMatch(),updatedMatch);
        result.add(updatedMatch);

        return result;
    }

    @Override
    public List<String> getAllPlayersUsername() {
        return new ArrayList<>(playerList.keySet());
    }

    @Override
    public List<Player> getAllPlayers() {
        return  new ArrayList<>(playerList.values());
    }


}
