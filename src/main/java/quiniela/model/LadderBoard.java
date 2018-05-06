package quiniela.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "ladderBoard")
public class LadderBoard {

    private Long id;
    private String name;
    private String bgColor;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    private Set<LadderBoardPlayer> listPlayers = new HashSet<>();

    private Set<LadderBoardPlayer> listBannedPlayers = new HashSet<>();

    private String password;

    public Set<LadderBoardPlayer> getListBannedPlayers() {
        return listBannedPlayers;
    }

    public void setListBannedPlayers(Set<LadderBoardPlayer> listBannedPlayers) {
        this.listBannedPlayers = listBannedPlayers;
    }

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

    public Set<LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(Set<LadderBoardPlayer> listPlayers) {
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
