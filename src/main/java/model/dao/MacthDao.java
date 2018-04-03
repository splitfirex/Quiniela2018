package model.dao;

import model.Match;
import service.DBService;

public class MacthDao implements DBService<Match> {

    @Override
    public int insertObject(Match obj) {
        return 0;
    }

    @Override
    public int deleteObjectById(Match obj) {
        return 0;
    }

    @Override
    public Match selectObjectById(int id) {
        return null;
    }

    @Override
    public int updateObject(int id, Match obj) {
        return 0;
    }
}
