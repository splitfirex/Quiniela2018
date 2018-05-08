package quiniela.service2;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.utils.dom.DOMGroup;

import java.util.List;

@Service
public interface GroupService {

    List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName);

    List<DOMGroup> generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    List<DOMGroup> updatePlayerMatches(Long p, Long l, Long idMatch, Integer homeScore, Integer visitScore);


}
