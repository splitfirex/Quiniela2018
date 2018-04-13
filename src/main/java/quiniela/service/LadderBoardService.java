package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Player;

import java.util.List;

@Service
public interface LadderBoardService {

    LadderBoard createLadderBoard(String name, String password, Player p);

    LadderBoard joinLadderBoard(String name, String password, Player p);

    LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p);

    List<LadderBoard> listLadderBoard();

    List<LadderBoard> listLadderBoard(Player p);

    LadderBoard getLadderBoardByName(String name, Player p);

    LadderBoard getLadderBoard(String name);

}
