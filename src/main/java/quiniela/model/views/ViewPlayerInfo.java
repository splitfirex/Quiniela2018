package quiniela.model.views;

import quiniela.model.Player;

public class ViewPlayerInfo {
    private String username;
    private String url;
    private Integer points;

    public ViewPlayerInfo(Player player) {
        if(player == null) return;
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
