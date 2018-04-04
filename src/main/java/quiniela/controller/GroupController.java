package quiniela.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quiniela.model.Group;
import quiniela.model.Team;
import quiniela.service.GroupService;
import quiniela.service.TeamService;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/all")
    @ResponseBody
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @GetMapping("/id/{group}")
    @ResponseBody
    public Group getIdTeam(@PathVariable("group") Integer id) {
        return groupService.getGroupById(id);
    }

    @GetMapping("/name/{group}")
    @ResponseBody
    public Group getIdTeam(@PathVariable("group") String name) {
        return groupService.getGroupByName(name);
    }


}
