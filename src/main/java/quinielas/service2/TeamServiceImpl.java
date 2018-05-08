package quinielas.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import quinielas.repository2.DOMTeamRepository;
import quinielas.utils.dom.DOMTeam;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    DOMTeamRepository domTeamRepository;

    @Override
    public List<DOMTeam> getAllTeams() {
        return domTeamRepository.findAllOrdered(new Sort(Sort.Direction.ASC, "id"));
    }
}
