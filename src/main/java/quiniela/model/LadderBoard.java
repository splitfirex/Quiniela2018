package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "ladderBoard")
public class LadderBoard {

    private long id;
    private String name;

    private List<LadderBoardPlayer> listPlayers = new ArrayList<>();

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<LadderBoardPlayer> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public LadderBoardPlayer getPlayerByName(String username) {
        for (LadderBoardPlayer lbp : listPlayers) {
            if (lbp.getUsername().equals(username)) {
                return lbp;
            }
        }
        return null;
    }
}
