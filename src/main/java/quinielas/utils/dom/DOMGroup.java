package quinielas.utils.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import quinielas.model.PlayerGroup;
import quinielas.model.PlayerGroupDetail;

import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "domGroup")
@JsonInclude(JsonInclude.Include.NON_NULL)
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
            this.matches.add(new DOMMatch(this.name,(JSONObject) array.get(i)));
        }
    }

    public DOMGroup(DOMGroup domGroups){
        this.id = domGroups.id;
        this.order = domGroups.order;
        this.type = domGroups.type;
        this.name = domGroups.name;
        this.winner = domGroups.winner;
        this.runnerup = domGroups.runnerup;
        this.forced =false;
        this.matches.addAll(domGroups.matches);
    }

    @Id
    String id;
    Long idLadder;
    Long idPlayer;

    String name;
    String type;
    Short order;
    Boolean forced;
    Long winner;
    Long runnerup;
    List<DOMMatch> matches = new ArrayList<>();

    public Boolean getForced() {
        return forced;
    }

    public void setForced(Boolean forced) {
        this.forced = forced;
    }

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

            if(!team_group.keySet().contains(playerMatch.getHome_team())){
                team_group.put(playerMatch.getHome_team(), new PlayerGroupDetail(){{setId(playerMatch.getHome_team()); setP(0);}});
            }
            if(!team_group.keySet().contains(playerMatch.getAway_team())){
                team_group.put(playerMatch.getAway_team(), new PlayerGroupDetail(){{setId(playerMatch.getAway_team()); setP(0);}});
            }

            PlayerGroupDetail homeTeamDetail = team_group.get(playerMatch.getHome_team());
            PlayerGroupDetail awayTeamDetail = team_group.get(playerMatch.getAway_team());

            if (playerMatch.getAway_team() != null && playerMatch.getHome_result() != null) {

                homeTeamDetail.setP(playerMatch.getHome_result() > playerMatch.getAway_result() ? homeTeamDetail.getP() + 3 : homeTeamDetail.getP());
                awayTeamDetail.setP(playerMatch.getHome_result() < playerMatch.getAway_result() ? awayTeamDetail.getP() + 3 : awayTeamDetail.getP());
                homeTeamDetail.setP(playerMatch.getHome_result() == playerMatch.getAway_result() ? homeTeamDetail.getP() + 1 : homeTeamDetail.getP());
                awayTeamDetail.setP(playerMatch.getHome_result() == playerMatch.getAway_result() ? awayTeamDetail.getP() + 1 : awayTeamDetail.getP());

                homeTeamDetail.setPg(homeTeamDetail.getPg() + playerMatch.getHome_result().intValue());
                awayTeamDetail.setPg(awayTeamDetail.getPg() + playerMatch.getAway_result().intValue());

                homeTeamDetail.setNg(homeTeamDetail.getNg() + playerMatch.getAway_result().intValue());
                awayTeamDetail.setNg(awayTeamDetail.getNg() + playerMatch.getHome_result().intValue());
            }

        }

        PlayerGroup playerGroup = new PlayerGroup();
        playerGroup.setGroupName(this.name.toUpperCase().substring(this.name.length() - 1));
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

    public void updateStatus(List<DOMGroup> otherGroups) {
        if(this.type.equals("groups")) {
            PlayerGroup PG = generatePlayerGroup();
            this.setWinner(PG.getDetails().getFirst().getId());
            this.setRunnerup(PG.getDetails().get(1).getId());
        }else if(this.type.equals("knockout")){
            this.getMatches().stream().forEachOrdered(m ->{
                if(m.type.equals("qualified")){
                    Long homeTeam = otherGroups.stream().filter(g-> g.getName().replace("Group ","").equals(m.getHome_team_ph().split("_")[0])).findFirst().get().getWinner();
                    Long awayTeam = otherGroups.stream().filter(g-> g.getName().replace("Group ","").equals(m.getAway_team_ph().split("_")[0])).findFirst().get().getRunnerup();
                    m.setHome_team(homeTeam);
                    m.setAway_team(awayTeam);
                }else if(m.type.equals("winner")){
                    List<DOMMatch> allMatches = otherGroups.stream().map(v -> v.getMatches()).flatMap(List::stream).sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());
                    Long homeTeam = allMatches.stream().filter(f->f.getId()== Long.parseLong(m.getHome_team_ph().replaceAll("\\D+",""))).findFirst().get().calculateWinner();
                    Long awayTeam = allMatches.stream().filter(f->f.getId()== Long.parseLong(m.getAway_team_ph().replaceAll("\\D+",""))).findFirst().get().calculateWinner();
                    m.setHome_team(homeTeam);
                    m.setAway_team(awayTeam);
                }else if(m.type.equals("loser")){
                    List<DOMMatch> allMatches = otherGroups.stream().map(v -> v.getMatches()).flatMap(List::stream).sorted((f2, f1) -> f2.getDate().compareTo(f1.getDate())).collect(Collectors.toList());
                    Long homeTeam = allMatches.stream().filter(f->f.getId() == Long.parseLong(m.getHome_team_ph().replaceAll("\\D+",""))).findFirst().get().calculateLoser();
                    Long awayTeam = allMatches.stream().filter(f->f.getId() == Long.parseLong(m.getAway_team_ph().replaceAll("\\D+",""))).findFirst().get().calculateLoser();
                    m.setHome_team(homeTeam);
                    m.setAway_team(awayTeam);
                }
            });
        }
    }
}
