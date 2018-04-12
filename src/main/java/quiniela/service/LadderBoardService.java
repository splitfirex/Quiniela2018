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

    List<LadderBoard> getPublicLadderBoard();

    LadderBoard addPlayer(LadderBoard t, Player player, TypePlayerState state);

    LadderBoard removePlayer(LadderBoard t, Player player);

    LadderBoard setAdmin(LadderBoard t, Player player, boolean set);

    List<String> getAdminsByIdTournament(long idTournament);

    LadderBoard updateLadderBoard(LadderBoard l);

}
