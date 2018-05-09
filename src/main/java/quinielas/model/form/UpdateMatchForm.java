package quinielas.model.form;

public class UpdateMatchForm extends TokenAbleForm {
    private Long idMatch;
    private String laddername;
    private Integer homeScore;
    private Integer visitScore;

    private Integer homePenalty;
    private Integer visitPenalty;

    public Long getIdMatch() {
        return idMatch;
    }

    public Integer getHomePenalty() {
        return homePenalty;
    }

    public void setHomePenalty(Integer homePenalty) {
        this.homePenalty = homePenalty;
    }

    public Integer getVisitPenalty() {
        return visitPenalty;
    }

    public void setVisitPenalty(Integer visitPenalty) {
        this.visitPenalty = visitPenalty;
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

    public String getLaddername() {
        return laddername;
    }

    public void setLaddername(String laddername) {
        this.laddername = laddername;
    }
}
