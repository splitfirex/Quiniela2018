package quinielas.model.views;


import quinielas.model.Player;

public class ViewPlayerInfo extends TokenAbleView {

    private String username;

    public ViewPlayerInfo(Player playerByUsername) {
        super();
        if(playerByUsername == null ) return;
        username = playerByUsername.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
