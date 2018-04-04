package quiniela.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "group")
public class Group {

    @Id
    private long id;

    @Indexed(unique = true)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
