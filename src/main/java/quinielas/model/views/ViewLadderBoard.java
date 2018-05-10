package quinielas.model.views;

import quinielas.model.LadderBoard;
import quinielas.model.LadderBoardPlayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ViewLadderBoard extends TokenAbleView {


    private Long id;
    private String name;
    private List<LadderBoardPlayer> listPlayers;
    private Boolean isProtected;
    private String bgColor;

    public List<LadderBoardPlayer> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<LadderBoardPlayer> listPlayers) {
        this.listPlayers = listPlayers;
    }

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
        this.isProtected = board.getPassword() != null;
        this.setBgColor(board.getBgColor());

    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
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


    public Boolean getProtected() {
        return isProtected;
    }

    public void setProtected(Boolean aProtected) {
        isProtected = aProtected;
    }
}
