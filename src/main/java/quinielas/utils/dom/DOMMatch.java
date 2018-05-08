package quinielas.utils.dom;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "domMatch")
public class DOMMatch {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM/yyyy HH:mm");

    public DOMMatch() {
    }

    ;

    public DOMMatch(JSONObject o) throws JSONException {

        this.id = o.getLong("name");
        this.type = o.getString("type");

        if (this.type.equals("group")) {
            this.home_team = o.getLong("home_team");
            this.away_team = o.getLong("away_team");
        } else if (this.type.equals("qualified")) {
            this.home_team_ph = o.getString("home_team");
            this.away_team_ph = o.getString("away_team");
            this.home_team_ph = this.home_team_ph.split("_")[1].toUpperCase() + "_" + (this.home_team_ph.split("_")[0].equals("winner") ? 1 : 2);
            this.away_team_ph = this.away_team_ph.split("_")[1].toUpperCase() + "_" + (this.away_team_ph.split("_")[0].equals("winner") ? 1 : 2);
        } else if (this.type.equals("winner")) {
            this.home_team_ph = "WIN_" + o.getLong("home_team");
            this.away_team_ph = "WIN_" + o.getLong("away_team");
        } else if (this.type.equals("loser")) {
            this.home_team_ph = "LOS_" + o.getLong("home_team");
            this.away_team_ph = "LOS_" + o.getLong("away_team");

        }

        try {
            this.home_result = o.getInt("home_result");
        } catch (JSONException ex) {
            this.home_result = null;
        }

        try {
            this.away_result = o.getInt("away_result");
        } catch (JSONException ex) {
            this.away_result = null;
        }

        try {
            this.home_penalty = o.getInt("home_penalty");
        } catch (JSONException ex) {
            this.home_penalty = null;
        }

        try {
            this.away_penalty = o.getInt("away_penalty");
        } catch (JSONException ex) {
            this.away_penalty = null;
        }

        try {
            this.winner = o.getLong("winner");
        } catch (JSONException ex) {
            this.winner = null;
        }

        this.finished = o.getBoolean("finished");
        this.date = ZonedDateTime.parse(o.getString("date")).toInstant().toEpochMilli();

    }

    Long id;
    String type;
    String home_team_ph;
    String away_team_ph;
    Long home_team;
    Long away_team;
    Integer home_result;
    Integer away_result;
    Integer home_penalty;
    Integer away_penalty;
    Long winner;
    Long date;
    Integer status;
    Boolean finished;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHome_team_ph() {
        return home_team_ph;
    }

    public void setHome_team_ph(String home_team_ph) {
        this.home_team_ph = home_team_ph;
    }

    public String getAway_team_ph() {
        return away_team_ph;
    }

    public void setAway_team_ph(String away_team_ph) {
        this.away_team_ph = away_team_ph;
    }

    public Long getHome_team() {
        return home_team;
    }

    public void setHome_team(Long home_team) {
        this.home_team = home_team;
    }

    public Long getAway_team() {
        return away_team;
    }

    public void setAway_team(Long away_team) {
        this.away_team = away_team;
    }

    public Integer getHome_result() {
        return home_result;
    }

    public void setHome_result(Integer home_result) {
        this.home_result = home_result;
    }

    public Integer getAway_result() {
        return away_result;
    }

    public void setAway_result(Integer away_result) {
        this.away_result = away_result;
    }

    public Integer getHome_penalty() {
        return home_penalty;
    }

    public void setHome_penalty(Integer home_penalty) {
        this.home_penalty = home_penalty;
    }

    public Integer getAway_penalty() {
        return away_penalty;
    }

    public void setAway_penalty(Integer away_penalty) {
        this.away_penalty = away_penalty;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long compareMatch(DOMMatch match) {
        if (match.getAway_team() != this.getAway_team() || match.getHome_team() != this.getHome_team()) return 0L;

        if (match.getHome_result() == this.getHome_result() && match.getAway_result() == this.getAway_result())
            return 3L;

        if (match.getHome_result() > match.getAway_result() && this.getHome_result() > this.getAway_result()) return 1L;

        if (match.getHome_result() < match.getAway_result() && this.getHome_result() < this.getAway_result()) return 1L;

        return 0L;
    }

}
