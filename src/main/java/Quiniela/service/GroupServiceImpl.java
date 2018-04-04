package quiniela.service;

import quiniela.model.Group;

import java.util.ArrayList;
import java.util.List;

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
