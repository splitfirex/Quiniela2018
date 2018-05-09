package quinielas.model.views;

import quinielas.model.PlayerGroup;
import quinielas.utils.dom.DOMMatch;

import java.util.HashMap;
import java.util.Map;

public class ViewPlayerMatchesGroup {

    String username;
    Integer points;
    Map<Long, DOMMatch> matches = new HashMap<>();
    Map<String, PlayerGroup> groups = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Map<Long, DOMMatch> getMatches() {
        return matches;
    }

    public void setMatches(Map<Long, DOMMatch> matches) {
        this.matches = matches;
    }

    public Map<String, PlayerGroup> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, PlayerGroup> groups) {
        this.groups = groups;
    }
}
