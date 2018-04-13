package quiniela.model;

import quiniela.model.enums.TypePlayerState;

public class LadderBoardPlayer {

    private Long id;
    private boolean isAdmin;
    private boolean isActive;
    private Long points;

    public LadderBoardPlayer(){

    }

    public LadderBoardPlayer(Long id, boolean isAdmin,  boolean state) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.isActive = state;
        this.points = new Long(0);
    }

    public LadderBoardPlayer(Long id,  boolean state) {
        this.id = id;
        this.isAdmin = false;
        this.isActive = state;
        this.points = new Long(0);
    }

    public LadderBoardPlayer(Long id) {
        this.id = id;
        this.isAdmin = false;
        this.isActive = false;
        this.points = new Long(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

}
