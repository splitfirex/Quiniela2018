package service;

import model.Group;
import model.Team;
import model.enums.GroupName;

import java.util.List;

public interface GroupService {

    static GroupService instance = new GroupServiceImpl();

    List<Group> getAllGroups();

    Group getGroupByName(String groupName);

    void setGroup(Group groupName);

}
