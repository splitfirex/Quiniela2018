package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;
import quiniela.model.enums.TypePlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "ladderBoard")
public class LadderBoard {

    private long id;
    private String name;

    private HashMap<String,LadderBoardPlayer> listPlayers = new HashMap<>();

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

    public HashMap<String, LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(HashMap<String, LadderBoardPlayer> listPlayers) {
        this.listPlayers = listPlayers;
    }
}
