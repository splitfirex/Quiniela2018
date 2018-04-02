package service;

import model.Group;
import model.Team;
import model.enums.GroupName;
import model.enums.TeamName;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupServiceImpl implements GroupService {

    static private List<Group> listGroup = new ArrayList<>();

    @Override
    public List<Group> getAllGroups() {
        return listGroup;
    }

    @Override
    public Group getGroupByName(String groupName) {
        for(Group g : listGroup){
            if(g.getName().equals(groupName)) return g;
        }
        return null;
    }

    @Override
    public void setGroup(Group group) {
        listGroup.add(group);
    }

}
