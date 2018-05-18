package quinielas.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "ladderBoard")
public class LadderBoard {

    private Long id;
    private String name;
    private String bgColor;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    private List<LadderBoardPlayer> listPlayers = new LinkedList<>();

    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
