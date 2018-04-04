package quiniela.repository;

import com.mongodb.client.result.UpdateResult;
import quiniela.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;



public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long updateMatch(long idPlayer, long idMatch, String homeTeam, String visitorTeam, int homeScore, int visitorScore) {

        Query query = new Query(Criteria.where("idPlayer").is(idPlayer).and("matchList._id").is(idMatch));
        Update update = new Update();
        update.set("homeTeam", homeTeam);
        update.set("visitorTeam", homeTeam);
        update.set("scoreHomeTeam", homeScore);
        update.set("scoreVisitorTeam", visitorScore);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Player.class);
        if(result!=null)
            return result.getModifiedCount();
        else
            return 0;
    }
}
