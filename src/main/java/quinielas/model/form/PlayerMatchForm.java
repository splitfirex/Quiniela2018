package quinielas.model.form;

public class PlayerMatchForm extends TokenAbleForm {

    private String laddername;
    private String username;

    public String getLaddername() {
        return laddername;
    }

    public void setLaddername(String laddername) {
        this.laddername = laddername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
