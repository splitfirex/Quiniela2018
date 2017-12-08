package net.splitfire.quiniela.service;

import net.splitfire.quiniela.model.Group;

import java.util.List;

public interface GroupService {

    List<Group> getAllGroups();

    Group getGroupByTeam();

}
