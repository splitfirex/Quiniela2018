package quiniela.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quiniela.repository2.DOMGroupRepository;
import quiniela.utils.dom.DOMGroup;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    List<DOMGroup> deafultDOMgroups = new ArrayList<>();

    @Autowired
    DOMGroupRepository domGroupRepository;

    @Override
    public List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName) {
        return domGroupRepository.findAllByIdPlayerAndIdLadder(idUsuario,ladderName, new Sort(Sort.Direction.ASC, "name"));
    }

    @Override
    public List<DOMGroup> generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        List<DOMGroup> playerGroups = new ArrayList<>();
        for(DOMGroup valor : deafultDOMgroups){
            playerGroups.add(new DOMGroup(valor));
        }
        domGroupRepository.saveAll(playerGroups);
        return playerGroups;
    }

    @Override
    public void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        domGroupRepository.deleteAll(domGroupRepository.findAllByIdPlayerAndIdLadder(idUsuario,ladderName, new Sort(Sort.Direction.ASC, "name")));
    }

    @Override
    public List<DOMGroup> updatePlayerMatches(Long p, Long l, Long idMatch, Integer homeScore, Integer visitScore) {
        return null;
    }
}
