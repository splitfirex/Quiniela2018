package net.splitfire.quiniela.model;

import java.util.Date;

public class Match {

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    private int order;
    private Date matchDate;
    private Team homeTeam;
    private Team awayTeam;
    private String location;
    private int scoreHomeTeam;
    private int scoreAwayTeam;
    private boolean penalties;

    public Match(int order, Date matchDate, Team homeTeam, Team awayTeam, String location) {
        this.order = order;
        this.matchDate = matchDate;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Match{" +
                "order=" +order +
                ", matchDate=" + matchDate +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", location='" + location + '\'' +
                ", scoreHomeTeam=" + scoreHomeTeam +
                ", scoreAwayTeam=" + scoreAwayTeam +
                ", penalties=" + penalties +
                '}';
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScoreHomeTeam() {
        return scoreHomeTeam;
    }

    public void setScoreHomeTeam(int scoreHomeTeam) {
        this.scoreHomeTeam = scoreHomeTeam;
    }

    public int getScoreAwayTeam() {
        return scoreAwayTeam;
    }

    public void setScoreAwayTeam(int scoreAwayTeam) {
        this.scoreAwayTeam = scoreAwayTeam;
    }

    public boolean isPenalties() {
        return penalties;
    }

    public void setPenalties(boolean penalties) {
        this.penalties = penalties;
    }
}
