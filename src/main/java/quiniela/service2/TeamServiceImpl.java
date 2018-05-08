package quiniela.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.repository2.DOMTeamRepository;
import quiniela.service2.model.Team;
import quiniela.utils.dom.DOMTeam;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    DOMTeamRepository domTeamRepository;

    @Override
    public List<DOMTeam> getAllTeams() {
        return domTeamRepository.findAll();
    }
}
