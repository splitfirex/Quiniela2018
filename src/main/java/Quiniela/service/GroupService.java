package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Group;
import quiniela.model.LadderBoard;
import quiniela.model.Player;

import java.util.List;

@Service
public interface GroupService {

    List<Group> getAllGroups();

    Group getGroupByName(String groupName);

    Group getGroupById(Integer idGroup);

    void createPlayerGroup(LadderBoard l, Player player);

    void deletePlayerGroup(LadderBoard l, Player player);

}
