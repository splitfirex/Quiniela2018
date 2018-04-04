package quiniela.service;

import quiniela.model.Group;

import java.util.List;

public interface GroupService {

    static GroupService instance = new GroupServiceImpl();

    List<Group> getAllGroups();

    Group getGroupByName(String groupName);

    void setGroup(Group groupName);

}
