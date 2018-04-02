package model.views;

import model.enums.TeamName;

public class TeamGroup {

    private TeamName name;
    private int points = 0;
    private int positiveGoals = 0;
    private int negativeGoals = 0;

    public TeamName getName() {
        return name;
    }

    public void setName(TeamName name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPositiveGoals() {
        return positiveGoals;
    }

    public void setPositiveGoals(int positiveGoals) {
        this.positiveGoals = positiveGoals;
    }

    public int getNegativeGoals() {
        return negativeGoals;
    }

    public void setNegativeGoals(int negativeGoals) {
        this.negativeGoals = negativeGoals;
    }
}
