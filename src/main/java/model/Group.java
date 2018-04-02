package model;

import model.enums.GroupName;
import model.enums.TeamName;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String name;
    private List<String> teams = new ArrayList<>();

    public Group addTeam(String team){
        teams.add(team);
        return this;
    }

    public List<String> getTeams() {
        return teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }
}
