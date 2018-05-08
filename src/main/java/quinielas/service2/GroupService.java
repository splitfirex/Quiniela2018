package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.Player;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.util.List;

@Service
public interface GroupService {

    List<DOMMatch> getMatchesByPlayerAndLadder(Long idUsuario, Long ladderName);

    List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName);

    void generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    List<DOMGroup> updatePlayerMatches(Long p, Long l, Long idMatch, Integer homeScore, Integer visitScore);

    void setDefaultList(List<DOMGroup> groups);

}
