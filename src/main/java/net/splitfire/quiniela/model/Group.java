package net.splitfire.quiniela.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Team> teams = new ArrayList<>();
    private String groupName;
    private Team classified_1;
    private Team classified_2;

    public Group(List<Team> group, String groupName) {
        this.teams = group;
        this.groupName = groupName;
    }

    public List<Team> getGroup() {
        return teams;
    }

    public void setGroup(List<Team> group) {
        this.teams = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Team getClassified_1() {
        return classified_1;
    }

    public void setClassified_1(Team classified_1) {
        this.classified_1 = classified_1;
    }

    public Team getClassified_2() {
        return classified_2;
    }

    public void setClassified_2(Team classified_2) {
        this.classified_2 = classified_2;
    }

    @Override
    public String toString() {
        return "Group{" +
                "teams=" + teams +
                ", groupName='" + groupName + '\'' +
                ", classified_1=" + classified_1 +
                ", classified_2=" + classified_2 +
                '}';
    }
}
