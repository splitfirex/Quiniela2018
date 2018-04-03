package model.dao;

import model.Team;
import service.DBService;

public class TeamDao implements DBService<Team> {


    @Override
    public int insertObject(Team obj) {
        return 0;
    }

    @Override
    public int deleteObjectById(Team obj) {
        return 0;
    }

    @Override
    public Team selectObjectById(int id) {
        return null;
    }

    @Override
    public int updateObject(int id, Team obj) {
        return 0;
    }
}
