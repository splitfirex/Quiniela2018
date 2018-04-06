package quiniela.model;

import org.springframework.data.annotation.Id;

public class PlayerMatch {

    @Id
    private long id;
    private String hTeam;
    private String vTeam;
    private Long hScore;
    private Long vScore;

    public String gethTeam() {
        return hTeam;
    }

    public void sethTeam(String hTeam) {
        this.hTeam = hTeam;
    }

    public String getvTeam() {
        return vTeam;
    }

    public void setvTeam(String vTeam) {
        this.vTeam = vTeam;
    }

    public Long gethScore() {
        return hScore;
    }

    public void sethScore(Long hScore) {
        this.hScore = hScore;
    }

    public Long getvScore() {
        return vScore;
    }

    public void setvScore(Long vScore) {
        this.vScore = vScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
