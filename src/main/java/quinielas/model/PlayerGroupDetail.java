package quinielas.model;

public class PlayerGroupDetail {

    private Long id;

    private Integer p = 0;
    private Integer pg = 0;
    private Integer ng = 0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getPg() {
        return pg;
    }

    public void setPg(Integer pg) {
        this.pg = pg;
    }

    public Integer getNg() {
        return ng;
    }

    public void setNg(Integer ng) {
        this.ng = ng;
    }
}
