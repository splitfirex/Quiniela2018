package model.views;

import model.Match;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class ViewPlayerMatchesGroups {

    private String username;
    private String url;
    private Integer points;
    private List<Match> listMatch = new ArrayList<>();

    public ViewPlayerMatchesGroups(Player player) {
        this.username = player.getUsername();
        this.points = player.getPoints();
        this.listMatch = player.getMatchList();
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPoints() {
        return points;
    }

}
