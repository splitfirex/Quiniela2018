package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.Player;
import quinielas.model.PlayerGroup;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.util.Collections;
import java.util.List;

@Service
public interface GroupService {

    List<DOMMatch> getMatchesByPlayerAndLadder(Long idUsuario, Long ladderName);

    List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName);

    void generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName, String type);

    void generateDemoGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName);

    void updateGroup(DOMGroup group);

    void updateGroup(List<DOMGroup> group);

    void updatePlayerMatches(Player player, LadderBoard l, List<DOMMatch> match);

    List<DOMGroup> getAllGroups();

    List<DOMGroup> getAllUnforcedGroups();
}
