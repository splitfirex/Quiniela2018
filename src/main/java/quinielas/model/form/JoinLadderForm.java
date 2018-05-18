package quinielas.model.form;

public class JoinLadderForm extends TokenAbleForm {

    private String laddername;
    private String password;
    private Long idLadder;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdLadder() {
        return idLadder;
    }

    public void setIdLadder(Long idLadder) {
        this.idLadder = idLadder;
    }

    public String getLaddername() {
        return laddername;
    }

    public void setLaddername(String laddername) {
        this.laddername = laddername;
    }
}
