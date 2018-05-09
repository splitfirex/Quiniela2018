package quinielas.repository2;

import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import quinielas.model.LadderBoard;
import quinielas.utils.dom.DOMGroup;
import quinielas.utils.dom.DOMMatch;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DOMGroupCustomRepositoryImpl implements DOMGroupCustomRepository {


    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Override
    public void updateGroupsMatchesByNearDate() {

        Long time = ZonedDateTime.now().plusDays(1).toInstant().toEpochMilli();
        Query query = new Query(Criteria.where("matches").elemMatch(Criteria.where("date").lte(time).and("finished").is(new Boolean(false)).and("editable").is(new Boolean(true))));
        query.fields().elemMatch("matches", Criteria.where("date").lte(time).and("finished").is(new Boolean(false)).and("editable").is(new Boolean(true)));

        Update update = new Update();
        update.set("matches.$.editable", false);
        mongoTemplate.updateMulti(query, update,DOMGroup.class);
    }

    @Override
    public void getNextMatches(Long idPlayer, Long idLadder) {

    }

}
