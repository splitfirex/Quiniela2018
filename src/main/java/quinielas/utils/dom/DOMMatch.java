package quinielas.utils.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "domMatch")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DOMMatch {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM/yyyy HH:mm");

    public DOMMatch() {
       calculateLoser();
       calculateWinner();
    }


    public DOMMatch(String groupname,JSONObject o) throws JSONException {

        this.id = o.getLong("name");
        this.type = o.getString("type");
        this.groupname= groupname;
        this.editable =  true;

        if (this.type.equals("group")) {
            this.home_team = o.getLong("home_team");
            this.away_team = o.getLong("away_team");
        } else if (this.type.equals("qualified")) {
            this.home_team_ph = o.getString("home_team");
            this.away_team_ph = o.getString("away_team");
            this.home_team_ph = this.home_team_ph.split("_")[1].toUpperCase() + "_" + (this.home_team_ph.split("_")[0].equals("winner") ? 1 : 2);
            this.away_team_ph = this.away_team_ph.split("_")[1].toUpperCase() + "_" + (this.away_team_ph.split("_")[0].equals("winner") ? 1 : 2);
        } else if (this.type.equals("winner")) {
            this.home_team_ph = "W" + o.getLong("home_team");
            this.away_team_ph = "W" + o.getLong("away_team");
        } else if (this.type.equals("loser")) {
            this.home_team_ph = "L" + o.getLong("home_team");
            this.away_team_ph = "L" + o.getLong("away_team");

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
    Long loser;
    Long date;
    Integer status;
    Boolean finished;
    Boolean editable;
    List<Long> references;
    String groupname;

    public Long calculateLoser(){
        if(this.winner== null && this.home_team != null && this.away_team!= null ){
            if(this.home_result == this.away_result){
                this.loser = this.home_penalty < this.away_penalty? this.home_team : this.away_team;
            }else{
                this.loser = this.home_result < this.away_result ? this.home_team : this.away_team;
            }

        }
        return this.loser;
    }

    public Long getLoser() {

        return loser;
    }

    public void setLoser(Long loser) {
        this.loser = loser;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<Long> getReferences() {
        if(this.references == null){
            this.references = new ArrayList<>();
        }
        return references;
    }

    public void setReferences(List<Long> references) {
        this.references = references;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

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

    public Long calculateWinner(){
        if(this.winner== null && this.home_team != null && this.away_team!= null ){
            if(this.home_result == this.away_result){
                this.winner = this.home_penalty > this.away_penalty? this.home_team : this.away_team;
            }else{
                this.winner = this.home_result > this.away_result ? this.home_team : this.away_team;
            }

        }
        return this.getWinner();
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
        if (this.getAway_result() == null || this.getHome_result() == null) return null;
        if(match.getAway_result() == null || match.getHome_result() == null) return 0L;
        if (match.getAway_result() == null || match.getHome_result() == null ||
                this.getAway_result() == null || this.getHome_result() == null) return null;

        if (match.getAway_team() != this.getAway_team() || match.getHome_team() != this.getHome_team()) return 0L;

        if (match.getHome_result() == this.getHome_result() && match.getAway_result() == this.getAway_result())
            return 3L;

        if (match.getHome_result() > match.getAway_result() && this.getHome_result() > this.getAway_result()) return 1L;

        if (match.getHome_result() < match.getAway_result() && this.getHome_result() < this.getAway_result()) return 1L;

        return 0L;
    }

}
