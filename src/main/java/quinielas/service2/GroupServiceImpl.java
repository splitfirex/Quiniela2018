package quinielas.service2;

import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quinielas.model.LadderBoard;
import quinielas.model.Player;
import quinielas.model.PlayerGroup;
import quinielas.repository2.DOMGroupRepository;
import quinielas.utils.RESTClient;
import quinielas.utils.ScoreMath;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Autowired
    private ScoreMath scoreMath;

    @Autowired
    DOMGroupRepository domGroupRepository;

    @Autowired
    LadderboardService ladderBoardService;

    @Value("${clean_and_build}")
    Boolean cleanAndBuild;

    @Autowired
    RESTClient restClient;

    @PostConstruct
    private void init() {
        if (cleanAndBuild) {
            domGroupRepository.deleteAll();
            restClient.requestData();
        }
    }

    private List<DOMGroup> getDefaultGroups() {
        return domGroupRepository.findAllByIdPlayerAndIdLadder(null, null, new Sort(Sort.Direction.ASC, "order"));
    }

    @Override
    public List<DOMMatch> getMatchesByPlayerAndLadder(Long idUsuario, Long ladderName) {
        return domGroupRepository
                .findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order"))
                .stream().map(v -> v.getMatches()).flatMap(List::stream)
                .sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());
    }

    @Override
    public List<DOMGroup> getGroupsByPlayerAndLadder(Long idUsuario, Long ladderName) {
        return domGroupRepository
                .findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order"))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        domGroupRepository.saveAll(getDefaultGroups().stream().map(v -> {
            DOMGroup d = new DOMGroup(v);
            d.setIdLadder(ladderName);
            d.setIdPlayer(idUsuario);
            d.setId(Generators.randomBasedGenerator().generate().toString());
            return d;
        }).collect(Collectors.toList()));
    }

    @Override
    public void generateDemoGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        Random rand = new Random();
        domGroupRepository.saveAll(getDefaultGroups().stream().map(v -> {
            DOMGroup d = new DOMGroup(v);
            d.setIdLadder(ladderName);
            d.setIdPlayer(idUsuario);
            d.setId(Generators.randomBasedGenerator().generate().toString());
            return d;
        }).collect(Collectors.toList()));


        List<DOMGroup> list = domGroupRepository.findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order"));
        list.stream()
                .forEach(p -> {
                    p.getMatches().stream()
                            .forEach(m -> {
                                m.setHome_result(rand.nextInt(5));
                                m.setAway_result(rand.nextInt(5));
                                if (m.getHome_result().equals(m.getAway_result())) {
                                    m.setHome_penalty(rand.nextInt(6));
                                    m.setAway_penalty(rand.nextInt(6));
                                }
                            });
                    p.updateStatus();
                });
        domGroupRepository.saveAll(list);
    }

    @Override
    public void deleteGroupsForPlayerAndLadder(Long idUsuario, Long ladderName) {
        domGroupRepository.deleteAll(domGroupRepository.findAllByIdPlayerAndIdLadder(idUsuario, ladderName, new Sort(Sort.Direction.ASC, "order")));
    }

    @Override
    public void updateGroup(DOMGroup group) {
        domGroupRepository.save(group);
    }

    @Override
    public List<PlayerGroup> updatePlayerMatches(Player player, LadderBoard l, List<DOMMatch> match) {
        List<DOMGroup> group = getGroupsByPlayerAndLadder(player.getId(), l.getId());
        List<Long> matchesId = match.stream().map(m -> m.getId()).collect(Collectors.toList());
        group.stream()
                .filter(g -> g.getMatches().stream()
                        .filter(m -> matchesId.contains(m.getId()))
                        .collect(Collectors.toList()).isEmpty()).forEach(gr -> {
                     gr.getMatches().stream().filter(m -> matchesId.contains(m.getId())).forEach(rr -> {
                         DOMMatch mm = match.stream().filter( jj -> jj.getId()==rr.getId()).findFirst().get();
                         rr.setHome_result(mm.getHome_result());
                         rr.setAway_result(mm.getAway_result());
                         rr.setHome_penalty(mm.getHome_penalty());
                         rr.setAway_penalty(mm.getAway_penalty());
                         rr.setHome_team(mm.getHome_team());
                         rr.setAway_team(mm.getAway_team());
                     });
                }
        );

       /*List<DOMGroup> editgroup = group.stream().filter(g -> !g.getMatches().stream()
                .filter(gp ->
                        gp.getId() == match.getId()).collect(Collectors.toList()).isEmpty())
                .collect(Collectors.toList());
        editgroup.get(0).getMatches().stream().filter(gp -> gp.getId() == match.getId()).forEach(rr -> {
            rr.setHome_result(match.getHome_result());
            rr.setAway_result(match.getAway_result());
            rr.setHome_penalty(match.getHome_penalty());
            rr.setAway_penalty(match.getAway_penalty());
            rr.setHome_team(match.getHome_team());
            rr.setAway_team(match.getAway_team());
        });
*/

        domGroupRepository.saveAll(group);
        if (l.getName().equals(genericLaddername)) {
            scoreMath.updatesPoints();
        }

        List<PlayerGroup> groups = new ArrayList<>();
        group.stream().forEachOrdered(g -> groups.add(g.generatePlayerGroup()));

        return groups.stream().filter(x -> x != null).collect(Collectors.toList());
    }

}
