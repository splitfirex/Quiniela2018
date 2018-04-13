package quiniela.model.views;

import quiniela.model.LadderBoard;
import quiniela.model.LadderBoardPlayer;
import quiniela.model.enums.TypePlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewLadderBoard extends TokenAbleView {


    private Long id;
    private String name;
    private Map<String, LadderBoardPlayer> listPlayers;


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

    public Map<String, LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(Map<String, LadderBoardPlayer> listPlayers) {
        this.listPlayers = listPlayers;
    }
}
