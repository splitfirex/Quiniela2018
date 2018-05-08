package quinielas.service2;

import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quinielas.repository2.DOMGroupRepository;
import quinielas.utils.RESTClient;
import quinielas.utils.ScoreMath;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    public List<DOMGroup> deafultDOMgroups = new ArrayList<>();

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Autowired
    private ScoreMath scoreMath;

    @Autowired
    DOMGroupRepository domGroupRepository;

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @Autowired
    RESTClient restClient;

    @PostConstruct
    private void init() {
        if(cleanAndBuild) {
            domGroupRepository.deleteAll();
            restClient.requestData();
        }
    }

    @Override
    public List<DOMMatch> getMatchesByPlayerAndLadder(Long idUsuario, Long ladderName) {
        return domGroupRepository
                .findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order"))
                .stream().map(v -> v.getMatches()).flatMap(List::stream)
                .sorted((f2, f1)-> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());
    }

    @Override
    public List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName) {
        return domGroupRepository
                .findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order"))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        domGroupRepository.saveAll(deafultDOMgroups.stream().map(v -> {
            DOMGroup d = new DOMGroup(v); d.setIdLadder(ladderName); d.setIdPlayer(idUsuario);
            d.setId(Generators.randomBasedGenerator().generate().toString());
            return d;
        }).collect(Collectors.toList()));
    }

    @Override
    public void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        domGroupRepository.deleteAll(domGroupRepository.findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order")));
    }

    @Override
    public List<DOMGroup> updatePlayerMatches(Long p, Long l, Long idMatch, Integer homeScore, Integer visitScore) {
        DOMGroup group = domGroupRepository.findOneByIdPlayerAndIdLadderAndIdMatch(p, l, idMatch);
        group.getMatches().stream().filter(gp -> gp.getId() == idMatch).forEach(gp -> gp.setHome_result(homeScore));
        domGroupRepository.save(group);

        if (l.equals(genericLaddername)) {
            scoreMath.updatesPoints();
        }

        return getGroupsByPlayerAndLadder(p, l);
    }

    @Override
    public void setDefaultList(List<DOMGroup> groups) {
        deafultDOMgroups.addAll(groups);
    }
}
