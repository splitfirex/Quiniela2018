package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.LadderBoardPlayer;
import quinielas.model.Player;

import java.util.List;

public interface LadderboardService {

    LadderBoard createLadderBoard(String name, String password, Player p, String type, boolean encode);

    LadderBoard joinLadderBoard(String name, String password, Player p);

    void createLadderBoardDemo(String name, String password, Player p);

    void joinLadderBoardDemo(String name, String password, Player p);

    LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p);

    List<LadderBoard> listCompleteLadderBoard();

    List<LadderBoard> listLadderBoard();

    List<LadderBoard> listLadderBoard(Player p);

    LadderBoard getLadderBoardByName(String name, Player p);

    LadderBoard getLadderBoard(String name);

    LadderBoard updateLadderboardColor(LadderBoard l);

    LadderBoard updateLadderBoard(LadderBoard l);

    Long getGenericLadderBoardId();

    void resetPassword(String name);
}
