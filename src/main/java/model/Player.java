package model;

import model.enums.GroupName;
import model.enums.TeamName;
import model.views.TeamGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private String imageUrl;
    private String username;
    private String password;

    private Integer points;
    private List<Match> matchList = new ArrayList<>();
    private Map<GroupName,List<TeamGroup>> groupTeams= new HashMap<>();

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

    public Map<GroupName, List<TeamGroup>> getGroupTeams() {
        return groupTeams;
    }

    public void setGroupTeams(Map<GroupName, List<TeamGroup>> groupTeams) {
        this.groupTeams = groupTeams;
    }
}
