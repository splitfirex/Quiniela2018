package quiniela.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerMatch")
public class PlayerMatch {

    @Id
    private long id;

    private Long idMatch;
    private Long idPlayer;
    private Long idLadder;

    private Long hT;
    private Long vT;
    private Integer hS;
    private Integer vS;

    public Long getIdMatch() {
        return idMatch;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Long getIdLadder() {
        return idLadder;
    }

    public void setIdLadder(Long idLadder) {
        this.idLadder = idLadder;
    }

    public Long gethT() {
        return hT;
    }

    public void sethT(Long hT) {
        this.hT = hT;
    }

    public Long getvT() {
        return vT;
    }

    public void setvT(Long vT) {
        this.vT = vT;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }
}
