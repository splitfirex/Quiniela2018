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
    public void generateGroupsForPlayerAndLadder(Long idUsuario, Long ladderName, String type) {
        domGroupRepository.saveAll(getDefaultGroups().stream().map(v -> {
            DOMGroup d = new DOMGroup(v);
            d.setIdLadder(ladderName);
            d.setIdPlayer(idUsuario);
            if(type.equals("Completo")){
                d.setForced(true);
            }else{
                d.setForced(false);
            }
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
                .forEachOrdered(p -> {
                    p.getMatches().stream()
                            .forEach(m -> {
                                m.setHome_result(rand.nextInt(5));
                                m.setAway_result(rand.nextInt(5));
                                if (m.getHome_result().equals(m.getAway_result())) {
                                    m.setHome_penalty(rand.nextInt(6));
                                    m.setAway_penalty(rand.nextInt(6));
                                }
                            });
                    p.updateStatus(list);
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
    public void updateGroup(List<DOMGroup> group) {
        domGroupRepository.saveAll(group);
    }

    @Override
    public void updatePlayerMatches(Player player, LadderBoard l, List<DOMMatch> listMatch) {
        List<DOMGroup> group = getGroupsByPlayerAndLadder(player.getId(), l.getId());
        List<DOMMatch> matches = group.stream().map(v -> v.getMatches()).flatMap(List::stream)
                .filter( m-> m.getEditable())
                .sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());

        listMatch.stream().forEach( p->
                {
                 DOMMatch otherMatch = matches.stream().filter( m1 -> m1.getId() == p.getId()).findFirst().get();
                    otherMatch.setAway_penalty(p.getAway_penalty());
                    otherMatch.setHome_penalty(p.getHome_penalty());
                    otherMatch.setAway_result(p.getAway_result());
                    otherMatch.setHome_result(p.getHome_result());
                    otherMatch.setAway_team(p.getAway_team());
                    otherMatch.setHome_team(p.getHome_team());
                }
        );

        domGroupRepository.saveAll(group);
        if (l.getName().equals(genericLaddername)) {
            //demoContent(group);
            scoreMath.updatesPoints();
        }

        DOMMatch ma = listMatch.stream().filter(m -> m.getId() == 64L && m.getAway_team()!= null && m.getHome_team()!=null).findFirst().orElse(null);
        if(ma != null){
            l.getPlayerByName(player.getUsername()).setWinnerTeam(ma.calculateWinner());
            ladderBoardService.updateLadderBoard(l);
        }
    }

    private void demoContent(List<DOMGroup> group){
        Random rand = new Random();
        group.stream().filter(f-> f.getType().equals("groups")).forEachOrdered( g->{
            g.getMatches().stream().forEach(m->{
                m.setHome_result(rand.nextInt(5));
                m.setAway_result(rand.nextInt(5));
            });
            g.updateStatus(group);
        });
        group.stream().filter(f-> f.getName().equals("Round of 16")).forEachOrdered( g->{
            g.updateStatus(group);
        });
        domGroupRepository.saveAll(group);
    }

    @Override
    public List<DOMGroup> getAllGroups() {
        return domGroupRepository.findAll();
    }

    @Override
    public List<DOMGroup> getAllUnforcedGroups() {
        return domGroupRepository.findAllUnforcedGroups();
    }


}
