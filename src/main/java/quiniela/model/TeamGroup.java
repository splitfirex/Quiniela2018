package quiniela.model;

public class TeamGroup {

    private int points = 0;
    private long positiveGoals = 0;
    private long negativeGoals = 0;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getPositiveGoals() {
        return positiveGoals;
    }

    public void setPositiveGoals(long positiveGoals) {
        this.positiveGoals = positiveGoals;
    }

    public long getNegativeGoals() {
        return negativeGoals;
    }

    public void setNegativeGoals(long negativeGoals) {
        this.negativeGoals = negativeGoals;
    }
}
