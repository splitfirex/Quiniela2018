package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Group;

import java.util.List;

@Service
public interface GroupService {

    List<Group> getAllGroups();

    Group getGroupByName(String groupName);

    Group getGroupById(Integer idGroup);

}
