package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.LadderBoardPlayer;
import quinielas.model.Player;

import java.util.List;

public interface LadderboardService {

    LadderBoard createLadderBoard(String name, String password, Player p);

    LadderBoard joinLadderBoard(String name, String password, Player p);

    void createLadderBoardDemo(String name, String password, Player p);

    void joinLadderBoardDemo(String name, String password, Player p);

    LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p);

    List<LadderBoard> listLadderBoard();

    List<LadderBoard> listLadderBoard(Player p);

    LadderBoard getLadderBoardByName(String name, Player p);

    LadderBoard getLadderBoard(String name);

    LadderBoard getLadderBoard(Long name);

    LadderBoard updateUserStatus(LadderBoard l, LadderBoardPlayer lbp);

    LadderBoard updateLadderboardColor(LadderBoard l);

    LadderBoard updateLadderBoard(LadderBoard l);

    void resetPassword(String name);
}
