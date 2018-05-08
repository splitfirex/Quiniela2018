package quiniela.repository2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import quiniela.model.LadderBoard;
import quiniela.utils.dom.DOMGroup;
import quiniela.utils.dom.DOMMatch;

import java.util.ArrayList;
import java.util.List;

public class DOMGroupCustomRepositoryImpl implements DOMGroupCustomRepository {


    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${ladder.laddername}")
    String genericLaddername;

    @Override
    public List<DOMMatch> getMatchesByIdPlayerAndIdLadder(Long idPlayer, Long IdLadder){

        Query query = new Query(Criteria.where("idLadder").is(IdLadder).and("idPlayer").is(idPlayer));
        List<DOMMatch> matches = new ArrayList<>();
        List<DOMGroup> groups = mongoTemplate.find(query,DOMGroup.class);

        for(DOMGroup grupo: groups){
            matches.addAll(grupo.getMatches());
        }



        return matches;
    }

}
