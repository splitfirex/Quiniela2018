package quiniela.model.views;

import quiniela.model.LadderBoard;
import quiniela.model.LadderBoardPlayer;

import java.util.ArrayList;
import java.util.List;

public class ViewLadderBoard extends TokenAbleView {


    private Long id;
    private String name;
    private List<LadderBoardPlayer> listPlayers;


    public static List<ViewLadderBoard> fromList(List<LadderBoard> boards) {
        if (boards == null) return null;
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

    public List<LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<LadderBoardPlayer> listPlayers) {
        this.listPlayers = listPlayers;
    }
}
