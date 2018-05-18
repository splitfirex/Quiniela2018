package quinielas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import quinielas.model.LadderBoard;

public class LadderBoardCustomRepositoryImpl implements LadderBoardCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Override
    public LadderBoard findByIdOrIfActive(String ladderName, String username) {

        Query query;
        if (username == null && !ladderName.equals(genericLaddername)) {
            query = new Query(Criteria.where("name").is(ladderName).and("password").exists(false));
        } else {
            query = new Query(Criteria.where("name").is(ladderName)
                    .orOperator(Criteria.where("password").exists(true).and("listPlayers.username").is(username).and("listPlayers.isActive").is(true),
                            Criteria.where("password").exists(false)));
        }
        return mongoTemplate.findOne(query, LadderBoard.class);
    }
}
