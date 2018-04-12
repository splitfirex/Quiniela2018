package quiniela.model;

import java.util.HashMap;
import java.util.Map;

public class TeamGroup {

    private String name;
    Map<Long,TeamGroupDetails> teamGroupsDetails = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, TeamGroupDetails> getTeamGroupsDetails() {
        return teamGroupsDetails;
    }

    public void setTeamGroupsDetails(Map<Long, TeamGroupDetails> teamGroupsDetails) {
        this.teamGroupsDetails = teamGroupsDetails;
    }
}
