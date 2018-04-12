package quiniela.model;

import java.util.HashMap;

public class LadderBoardPlayer {

    private Long id;
    private Integer points;

    private HashMap<Long,PlayerMatch> listMatches = new HashMap<>();
    private HashMap<Long,TeamGroup> TeamGroup = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public HashMap<Long, PlayerMatch> getListMatches() {
        return listMatches;
    }

    public void setListMatches(HashMap<Long, PlayerMatch> listMatches) {
        this.listMatches = listMatches;
    }

    public HashMap<Long, quiniela.model.TeamGroup> getTeamGroup() {
        return TeamGroup;
    }

    public void setTeamGroup(HashMap<Long, quiniela.model.TeamGroup> teamGroup) {
        TeamGroup = teamGroup;
    }
}
