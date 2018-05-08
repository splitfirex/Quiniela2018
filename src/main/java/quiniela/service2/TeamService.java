package quiniela.service2;

import org.springframework.stereotype.Service;
import quiniela.service2.model.Team;
import quiniela.utils.dom.DOMTeam;

import java.util.List;

@Service
public interface TeamService {
    List<DOMTeam> getAllTeams();
}
