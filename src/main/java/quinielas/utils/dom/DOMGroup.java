package quinielas.utils.dom;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import quinielas.model.PlayerGroup;
import quinielas.model.PlayerGroupDetail;
import quinielas.model.PlayerMatch;
import quinielas.model.enums.TypeMatch;

import java.util.*;

@Document(collection = "domGroup")
public class DOMGroup {

    public DOMGroup(){};

    public DOMGroup(String id, short order,  String type, JSONObject object) throws JSONException {
        this.id = id;
        this.order = order;
        this.type = type;
        this.name = object.getString("name");
        try {
            this.winner = object.getLong("winner");
        } catch (JSONException ex) {
            this.winner = null;
        }
        try {
            this.runnerup = object.getLong("runnerup");
        } catch (JSONException ex) {
            this.runnerup = null;
        }
        JSONArray array = object.getJSONArray("matches");

        for (int i = 0; i < array.length(); i++) {
            this.matches.add(new DOMMatch((JSONObject) array.get(i)));
        }
    }

    public DOMGroup(DOMGroup domGroups){
        this.id = domGroups.id;
        this.order = domGroups.order;
        this.type = domGroups.type;
        this.name = domGroups.name;
        this.winner = domGroups.winner;
        this.runnerup = domGroups.runnerup;
        this.matches.addAll(domGroups.matches);
    }

    @Id
    String id;

    Long idLadder;

    Long idPlayer;

    String name;
    String type;
    Short order;
    Long winner;
    Long runnerup;
    List<DOMMatch> matches = new ArrayList<>();

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
    }

    public Long getIdLadder() {
        return idLadder;
    }

    public void setIdLadder(Long idLadder) {
        this.idLadder = idLadder;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public Long getRunnerup() {
        return runnerup;
    }

    public void setRunnerup(Long runnerup) {
        this.runnerup = runnerup;
    }

    public List<DOMMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<DOMMatch> matches) {
        this.matches = matches;
    }

    public PlayerGroup generatePlayerGroup(){
        if(!this.type.equals("groups")) return null;

        Map<Long, PlayerGroupDetail> team_group = new HashMap<>();

        for (DOMMatch playerMatch : this.matches) {
            if(team_group.keySet().contains(playerMatch.getHome_team())){
                team_group.put(playerMatch.getHome_team(), new PlayerGroupDetail(){{setP(playerMatch.getHome_team().intValue());}});
            }
            if(team_group.keySet().contains(playerMatch.getAway_team())){
                team_group.put(playerMatch.getAway_team(), new PlayerGroupDetail(){{setP(playerMatch.getAway_team().intValue());}});
            }

            PlayerGroupDetail homeTeamDetail = team_group.get(playerMatch.getHome_team());
            PlayerGroupDetail awayTeamDetail = team_group.get(playerMatch.getAway_team());

            homeTeamDetail.setP( playerMatch.getHome_result() > playerMatch.getAway_result() ? homeTeamDetail.getP() + 3 : 0 );
            awayTeamDetail.setP( playerMatch.getHome_result() < playerMatch.getAway_result() ? awayTeamDetail.getP() + 3 : 0 );
            homeTeamDetail.setP( playerMatch.getHome_result() == playerMatch.getAway_result() ? homeTeamDetail.getP() + 1 : 0 );
            awayTeamDetail.setP( playerMatch.getHome_result() == playerMatch.getAway_result() ? awayTeamDetail.getP() + 1 : 0 );

            homeTeamDetail.setPg( homeTeamDetail.getPg() +  playerMatch.getHome_result().intValue() );
            awayTeamDetail.setPg( awayTeamDetail.getPg() +  playerMatch.getAway_result().intValue() );

            homeTeamDetail.setPg( homeTeamDetail.getNg() +  playerMatch.getAway_result().intValue() );
            awayTeamDetail.setPg( awayTeamDetail.getNg() +  playerMatch.getHome_result().intValue() );

        }

        PlayerGroup playerGroup = new PlayerGroup();
        playerGroup.setIdPlayer(this.idPlayer);
        playerGroup.setIdLadder(this.idLadder);
        playerGroup.setDetails(new LinkedList<>(team_group.values()));

        Collections.sort(playerGroup.getDetails(), new Comparator<PlayerGroupDetail>() {
            @Override
            public int compare(PlayerGroupDetail d2, PlayerGroupDetail d1) {
                if (d1.getP().compareTo(d2.getP()) != 0) {
                    return d1.getP().compareTo(d2.getP());
                } else {
                    return ((Integer) (d1.getPg() - d1.getNg())).compareTo(d2.getPg() - d2.getNg());
                }
            }
        });

        return playerGroup;
    }

}
