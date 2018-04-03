package model.views;

import model.Match;
import model.Player;
import model.TeamGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPlayerMatchesGroups {

    private String username;
    private String url;
    private Integer points;
    private List<Match> listMatch;
    private Map<String, Map<String,TeamGroup>> groupTeams;

    public ViewPlayerMatchesGroups(Player player) {
        this.username = player.getUsername();
        this.points = player.getPoints();
        this.listMatch = player.getMatchList();
        this.groupTeams = player.getGroupTeams();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(List<Match> listMatch) {
        this.listMatch = listMatch;
    }

    public Map<String, Map<String, TeamGroup>> getGroupTeams() {
        return groupTeams;
    }

    public void setGroupTeams(Map<String, Map<String, TeamGroup>> groupTeams) {
        this.groupTeams = groupTeams;
    }
}
