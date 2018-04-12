package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;
import quiniela.model.enums.TypePlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "ladderBoard")
public class LadderBoard {

    private long id;
    private String name;

    private Map<String, TypePlayerState> listPlayers = new HashMap<String,TypePlayerState>();
    private List<String> listAdmins = new ArrayList<String>();
    private HashMap<Long, LadderBoardPlayer> ladderBoardPlayers = new HashMap<>();

    private String password;

    public Map<String, TypePlayerState> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(Map<String, TypePlayerState> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListAdmins() {
        return listAdmins;
    }

    public void setListAdmins(List<String> listAdmins) {
        this.listAdmins = listAdmins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Long, LadderBoardPlayer> getLadderBoardPlayers() {
        return ladderBoardPlayers;
    }

    public void setLadderBoardPlayers(HashMap<Long, LadderBoardPlayer> ladderBoardPlayers) {
        this.ladderBoardPlayers = ladderBoardPlayers;
    }

    public void addPlayer(Player p, List<PlayerMatch> matches, TypePlayerState state){
        getListPlayers().put(p.getUsername(),state);
        this.ladderBoardPlayers.put(p.getId(), new LadderBoardPlayer());
        for(PlayerMatch pm : matches) {
            this.ladderBoardPlayers.get(p.getId()).getListMatches().put(pm.getId(),pm);
        }
    }

    public void removePlayer(Player p){
        getListPlayers().remove(p.getUsername());
        getListAdmins().remove(p.getUsername());
        this.ladderBoardPlayers.put(p.getId(), new LadderBoardPlayer());
    }
}
