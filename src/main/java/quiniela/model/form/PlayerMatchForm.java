package quiniela.model.form;

public class PlayerMatchForm extends TokenAbleForm {

    private long id;
    private long idLadder;
    private Integer hS;
    private Integer vS;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdLadder() {
        return idLadder;
    }

    public void setIdLadder(long idLadder) {
        this.idLadder = idLadder;
    }

    public Integer gethS() {
        return hS;
    }

    public void sethS(Integer hS) {
        this.hS = hS;
    }

    public Integer getvS() {
        return vS;
    }

    public void setvS(Integer vS) {
        this.vS = vS;
    }
}
