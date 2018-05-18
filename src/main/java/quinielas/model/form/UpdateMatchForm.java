package quinielas.model.form;

import quinielas.utils.dom.DOMMatch;

import java.util.List;

public class UpdateMatchForm extends TokenAbleForm {

    private String password;
    private String laddername;
    List<DOMMatch> listMatches;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLaddername() {
        return laddername;
    }

    public void setLaddername(String laddername) {
        this.laddername = laddername;
    }

    public List<DOMMatch> getListMatches() {
        return listMatches;
    }

    public void setListMatches(List<DOMMatch> listMatches) {
        this.listMatches = listMatches;
    }
}
