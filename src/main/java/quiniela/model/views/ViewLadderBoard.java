package quiniela.model.views;

import quiniela.model.LadderBoard;
import quiniela.model.PlayerMatch;
import quiniela.model.TeamGroup;
import quiniela.model.enums.TypePlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewLadderBoard extends TokenAbleView {


    private Long id;
    private String name;
    private Map<String, TypePlayerState> listPlayers;
    private List<String> listAdmin;


    public static List<ViewLadderBoard> fromList(List<LadderBoard> boards) {
        List<ViewLadderBoard> result = new ArrayList<>();
        for (LadderBoard board : boards) {
            result.add(new ViewLadderBoard(board));
        }
        return result;
    }

    public ViewLadderBoard(LadderBoard board) {
        if (board == null) return;
        this.id = board.getId();
        this.name = board.getName();
        this.listPlayers = board.getListPlayers();
        this.listAdmin = board.getListAdmins();
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

    public Map<String, TypePlayerState> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(Map<String, TypePlayerState> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public List<String> getListAdmin() {
        return listAdmin;
    }

    public void setListAdmin(List<String> listAdmin) {
        this.listAdmin = listAdmin;
    }
}
