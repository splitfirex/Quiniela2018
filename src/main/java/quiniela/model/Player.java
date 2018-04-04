package quiniela.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "player")
public class Player{

    @Id
    private long id;
    private String imageUrl;

    @Indexed(unique = true)
    private String username;
    private String password;

    private Integer points;



    private List<PlayerMatch> matchList = new ArrayList<>();
    private Map<String, Map<String,TeamGroup>> groupTeams = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public List<PlayerMatch> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<PlayerMatch> matchList) {
        this.matchList = matchList;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Map<String, Map<String, TeamGroup>> getGroupTeams() {
        return groupTeams;
    }

    public void setGroupTeams(Map<String, Map<String, TeamGroup>> groupTeams) {
        this.groupTeams = groupTeams;
    }

    public void addMatches(List<Match> allMatches) {
        for(Match m : allMatches){
            PlayerMatch pm = new PlayerMatch();
            pm.setId(m.getId());
            pm.sethTeam(m.getHomeTeam());
            pm.setvTeam(m.getVisitorTeam());
            this.getMatchList().add(pm);
        }
    }
}
