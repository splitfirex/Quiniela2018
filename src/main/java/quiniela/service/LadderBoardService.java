package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.enums.TypePlayerState;

import java.util.List;
import java.util.Map;

@Service
public interface LadderBoardService {

    LadderBoard createLadderBoard(String name, Player p);

    LadderBoard joinLadderBoard(String name, Player p);

    LadderBoard leaveLadderBoard(LadderBoard ladderBoard, Player p);

    List<LadderBoard> listLadderBoard();

    List<LadderBoard> listLadderBoard(Player p);

}
