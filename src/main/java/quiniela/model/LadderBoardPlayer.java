package quiniela.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LadderBoardPlayer {


    private String username;
    private Boolean isAdmin;
    private Boolean isActive;
    private Long points;

    public Long getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Long winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    private Long winnerTeam;

    public LadderBoardPlayer(){

    }

    public LadderBoardPlayer(String username, boolean isAdmin, boolean state) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.isActive = state;
        this.points = new Long(0);
    }

    public LadderBoardPlayer(String username, boolean state) {
        this.username = username;
        this.isAdmin = false;
        this.isActive = state;
        this.points = new Long(0);
    }

    public LadderBoardPlayer(String username) {
        this.username = username;
        this.isAdmin = false;
        this.isActive = false;
        this.points = new Long(0);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
