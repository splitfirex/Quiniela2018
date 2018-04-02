package model;

import model.enums.TeamName;
import model.enums.TypeMatch;

import java.time.LocalDateTime;

public class Match {

    private int idMatch;
    private String homeTeam;
    private String visitorTeam;
    private Integer scoreHomeTeam;
    private Integer scoreVisitorTeam;
    private LocalDateTime date;
    private TypeMatch typeMatch;

    public Match(int idMatch, LocalDateTime date, TypeMatch type) {
        this.idMatch = idMatch;
        this.typeMatch = type;
        this.date = date;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public Match setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public Match setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
        return this;
    }

    public Integer getScoreHomeTeam() {
        return scoreHomeTeam;
    }

    public Match setScoreHomeTeam(Integer scoreHomeTeam) {
        this.scoreHomeTeam = scoreHomeTeam;
        return this;
    }

    public Integer getScoreVisitorTeam() {
        return scoreVisitorTeam;
    }

    public Match setScoreVisitorTeam(Integer scoreVisitorTeam) {
        this.scoreVisitorTeam = scoreVisitorTeam;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

