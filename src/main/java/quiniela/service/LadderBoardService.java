package quiniela.service;

import org.springframework.stereotype.Service;
import quiniela.model.LadderBoard;
import quiniela.model.Player;
import quiniela.model.enums.TypePlayerState;

import java.util.List;
import java.util.Map;

@Service
public interface LadderBoardService {

    List<LadderBoard> getAllTournaments();

    Map<String, TypePlayerState> getPlayersByIdTournament(long idTournament);

    LadderBoard getTournamentByName(String name);

    LadderBoard getTournamentById(Long id);

    LadderBoard createTournament(String name, String token, String password);

    List<LadderBoard> getLadderBoardsByUsername(String username);

    List<LadderBoard> getPublicLadderBoard();

    LadderBoard addPlayer(long idTournament, Player player, TypePlayerState state);

    LadderBoard removePlayer(long idTournament, Player player);

    LadderBoard setAdmin(long idTournament, Player player, boolean set);

    List<String> getAdminsByIdTournament(long idTournament);

    LadderBoard updateLadderBoard(LadderBoard l);

}
