package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.*;
import quiniela.repository.GroupRepository;
import quiniela.repository.PlayerGroupRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GroupServiceImpl implements GroupService {

    private AtomicLong counter = new AtomicLong();

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PlayerGroupRepository playerGroupRepository;

    @Autowired
    TeamService teamService;

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

    @Override
    public void createPlayerGroup(LadderBoard l, Player player) {
        List<Group> groups = getAllGroups();
        List<PlayerGroup> saveList = new ArrayList<>();
        for(Group group : groups){
            PlayerGroup playerGroup = new PlayerGroup();
            playerGroup.setId(counter.incrementAndGet());
            playerGroup.setIdLadder(l.getId());
            playerGroup.setIdPlayer(player.getId());
            playerGroup.setIdGroup(group.getId());

            LinkedList<PlayerGroupDetail> mapDetails = new LinkedList<>();
            for(String team : group.getTeams()){
                PlayerGroupDetail pgd = new PlayerGroupDetail();
                pgd.setId(teamService.getTeamIdByName(team));
                pgd.setNg(0);
                pgd.setP(0);
                pgd.setPg(0);

                mapDetails.add(pgd);
            }
            playerGroup.setDetails(mapDetails);
            saveList.add(playerGroup);
        }
        playerGroupRepository.saveAll(saveList);

    }

    @Override
    public void deletePlayerGroup(LadderBoard l, Player player) {
        playerGroupRepository.deleteAll(playerGroupRepository.findAllByUserIdAndLadderboardID(player.getId(),l.getId()));
    }

    @Override
    public List<PlayerGroup> getGroupsByPlayerLadder(LadderBoard l, Player p) {
        return playerGroupRepository.findAllByUserIdAndLadderboardID(p.getId(),l.getId());
    }

}
