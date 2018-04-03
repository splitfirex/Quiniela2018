package model.dao;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import model.Team;
import service.DBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeamDao {

    public static List<Team> getAllTeams(){
        Gson gson =new Gson();
        List<Team> result = new ArrayList<>();
        DBCollection teamTable = DBService.instance.getDBCollection("team");

        DBCursor cursor2 = teamTable.find();

        while (cursor2.hasNext()) {
            result.add(gson.fromJson(cursor2.next().toString(), Team.class));
        }
        return result;
    }

    public static void insertTeam(Team newTeam){
        Gson gson =new Gson();
        DBCollection teamTable = DBService.instance.getDBCollection("team");
        teamTable.insert(new BasicDBObject(gson.fromJson(gson.toJson(newTeam),Map.class)));
    }

    public static Team getTeamByName(String teamName){
        Gson gson =new Gson();
        DBCollection teamTable = DBService.instance.getDBCollection("team");
        DBCursor cursor2 =  teamTable.find(new BasicDBObject().append("name",teamName) );
        while (cursor2.hasNext()) {
            return gson.fromJson(cursor2.next().toString(), Team.class);
        }
        return null;
    }

}
