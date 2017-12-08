package net.splitfire.quiniela.controller;

import net.splitfire.quiniela.model.Group;
import net.splitfire.quiniela.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/groups/", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> listAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        if(groups.isEmpty()){
            return new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }

}
