package quiniela.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import quiniela.model.LadderBoard;
import quiniela.model.PlayerMatch;

import java.util.Date;
import java.util.List;

public class PlayerMatchCustomRepositoryImpl implements PlayerMatchCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<PlayerMatch> findbyBetweenDates(Date from, Date to) {
        return mongoTemplate.find(new Query(Criteria.where("date").gte(from).lte(to).exists(false)),PlayerMatch.class);
    }
}
