package quinielas.repository;

import org.springframework.stereotype.Repository;
import quinielas.model.LadderBoard;

@Repository
public interface LadderBoardCustomRepository {
    LadderBoard findByIdOrIfActive(String ladderName, String username);
}
