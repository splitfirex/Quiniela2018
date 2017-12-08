package net.splitfire.quiniela.service;

import net.splitfire.quiniela.model.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("groupService")
@Transactional
public class GroupServiceImpl implements GroupService{
    @Override
    public List<Group> getAllGroups() {
        return null;
    }

    @Override
    public Group getGroupByTeam() {
        return null;
    }
}
