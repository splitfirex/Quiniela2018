package quiniela.model.form;

public class JoinLadderForm extends TokenAbleForm {

    private String nameladder;
    private String password;
    private Long idLadder;

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

    public String getNameladder() {
        return nameladder;
    }

    public void setNameladder(String nameladder) {
        this.nameladder = nameladder;
    }
}
