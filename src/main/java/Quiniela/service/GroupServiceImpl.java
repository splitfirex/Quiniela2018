package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.Group;
import quiniela.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupByName(String groupName) {
       return groupRepository.findByName(groupName);
    }

    @Override
    public Group getGroupById(Integer idGroup) {
        return groupRepository.findById(idGroup);
    }

}
