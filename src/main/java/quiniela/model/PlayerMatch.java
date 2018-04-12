package quiniela.model;

import org.springframework.data.annotation.Id;

public class PlayerMatch {

    @Id
    private long id;

    private Long hT;
    private Long vT;
    private Integer hS;
    private Integer vS;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
