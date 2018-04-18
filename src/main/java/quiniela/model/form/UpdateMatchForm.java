package quiniela.model.form;

public class UpdateMatchForm extends TokenAbleForm {
    private Long idMatch;
    private String ladderName;
    private Integer homeScore;
    private Integer visitScore;

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public String getLadderName() {
        return ladderName;
    }

    public void setLadderName(String ladderName) {
        this.ladderName = ladderName;
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
