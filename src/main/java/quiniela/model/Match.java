package quiniela.model;

import quiniela.model.enums.TypeMatch;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "match")
public class Match{

    private long id;
    private long idPlayer;
    private String homeTeam;
    private String visitorTeam;
    private Integer scoreHomeTeam;
    private Integer scoreVisitorTeam;
    private LocalDateTime date;
    private TypeMatch typeMatch;

    public Match(int idMatch, LocalDateTime date, TypeMatch type) {
        this.id = idMatch;
        this.typeMatch = type;
        this.date = date;
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

    public TypeMatch getTypeMatch() {
        return typeMatch;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(long idPlayer) {
        this.idPlayer = idPlayer;
    }
}

