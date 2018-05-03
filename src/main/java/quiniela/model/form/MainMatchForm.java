package quiniela.model.form;

public class MainMatchForm {

    private String password;
    private Long idMatch;
    private Integer homeScore;
    private Integer visitScore;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getVisitScore() {
        return visitScore;
    }

    public void setVisitScore(Integer visitScore) {
        this.visitScore = visitScore;
    }
}
