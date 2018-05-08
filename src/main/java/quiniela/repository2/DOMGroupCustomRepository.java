package quiniela.repository2;

import org.springframework.stereotype.Repository;
import quiniela.utils.dom.DOMMatch;

import java.util.List;

@Repository
public interface DOMGroupCustomRepository{

    List<DOMMatch> getMatchesByIdPlayerAndIdLadder(Long idPlayer, Long IdLadder);
}
