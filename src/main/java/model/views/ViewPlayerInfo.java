package model.views;

import model.Match;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class ViewPlayerInfo {
    private String username;
    private String url;
    private Integer points;

    public ViewPlayerInfo(Player player) {
        this.username = player.getUsername();
        this.points = player.getPoints();
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
