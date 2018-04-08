package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.Player;
import quiniela.model.Tournament;

import java.util.List;

@Service
public interface TournamentService {

    List<Tournament> getAllTournaments();

    List<String> getPlayersByIdTournament(long idTournament);

    Tournament getTournamentByName(String name);

    Tournament createTournament(String name, String usernameAdmin);

    Tournament getTournamentByUsername(String username);

    Tournament addPlayer(long idTournament, Player username);

}
