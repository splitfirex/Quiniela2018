package quinielas.service2;

import org.springframework.stereotype.Service;
import quinielas.utils.dom.DOMTeam;

import java.util.List;

@Service
public interface TeamService {
    List<DOMTeam> getAllTeams();
}
