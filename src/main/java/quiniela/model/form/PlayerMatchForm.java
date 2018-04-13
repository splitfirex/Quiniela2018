package quiniela.model.form;

public class PlayerMatchForm extends TokenAbleForm {

    private String ladder;
    private String username;

    public String getLadder() {
        return ladder;
    }

    public void setLadder(String ladder) {
        this.ladder = ladder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
