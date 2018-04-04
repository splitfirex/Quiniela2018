package quiniela.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import quiniela.model.enums.TypeMatch;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "match")
public class Match{

    @Id
    private long id;

    @Indexed(unique = false)
    private Long idPlayer;

    private String homeTeam;
    private String visitorTeam;
    private Integer scoreHomeTeam;
    private Integer scoreVisitorTeam;
    private LocalDateTime date;
    private TypeMatch typeMatch;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Integer getScoreHomeTeam() {
        return scoreHomeTeam;
    }

    public void setScoreHomeTeam(Integer scoreHomeTeam) {
        this.scoreHomeTeam = scoreHomeTeam;
    }

    public Integer getScoreVisitorTeam() {
        return scoreVisitorTeam;
    }

    public void setScoreVisitorTeam(Integer scoreVisitorTeam) {
        this.scoreVisitorTeam = scoreVisitorTeam;
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

    public void setTypeMatch(TypeMatch typeMatch) {
        this.typeMatch = typeMatch;
    }
}

