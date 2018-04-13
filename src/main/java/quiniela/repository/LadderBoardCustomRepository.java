package quiniela.repository;

import org.springframework.stereotype.Repository;
import quiniela.model.LadderBoard;

@Repository
public interface LadderBoardCustomRepository {
    LadderBoard findByIdOrIfActive(String ladderName, String username);
}
