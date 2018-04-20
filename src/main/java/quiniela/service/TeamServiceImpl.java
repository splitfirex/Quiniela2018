package quiniela.service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import quiniela.model.Group;
import quiniela.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import quiniela.repository.GroupRepository;
import quiniela.repository.TeamRepository;
import quiniela.utils.CVSParser;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeamServiceImpl implements TeamService {

    static private AtomicLong counter = new AtomicLong();

    final static private Integer CSV_MAX_VALUES_TEAM = 5;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static HashMap<String,Long> idTeamName = new HashMap<>();

    private static HashMap<Long,String> nameTeamId = new HashMap<>();

    @PostConstruct
    private void init(){
        List<String> values = CVSParser.ParseMatches("teams.cvs");

        List<Team> inserTeam = new ArrayList<>();
        HashMap<String,Group> inserGroup = new HashMap<>();

        teamRepository.deleteAll();
        groupRepository.deleteAll();

        for (int i = CSV_MAX_VALUES_TEAM; i < values.size(); i += CSV_MAX_VALUES_TEAM) {
            if(values.get(i+1).contains("_")) continue;
            Team t = new Team();
            t.setId(Long.parseLong(values.get(i)));
            t.setName(values.get(i + 1));
            t.setShortName(values.get(i + 2));
            t.setGroup(values.get(i + 3));
            t.setFlagUrl(values.get(i + 4));
            inserTeam.add(t);

            idTeamName.put(t.getName(), t.getId());
            nameTeamId.put(t.getId(), t.getName());

            if(values.get(i + 3).equals("-")) continue;
            if (!inserGroup.keySet().contains(values.get(i + 3))) {
                Group group = new Group();
                group.setName(values.get(i + 3));
                group.setId(counter.incrementAndGet());
                group.addTeam(values.get(i + 1));
                inserGroup.put(values.get(i + 3), group);
            }else{
                Group group = inserGroup.get(values.get(i + 3));
                group.addTeam(values.get(i + 1));
            }
        }
        teamRepository.saveAll(inserTeam);
        groupRepository.saveAll(inserGroup.values());

    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeamById(long idTeam) {
        return teamRepository.findById(idTeam);
    }

    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<String> getTeamsByGroup(String idGroup) {
        return groupRepository.findByName(idGroup).getTeams();
    }

    @Override
    public Long getTeamIdByName(String name) {
        return idTeamName.get(name);
    }

    @Override
    public String getTeamNameById(Long name) {
        return nameTeamId.get(name);
    }

}
