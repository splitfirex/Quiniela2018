package model.dao;

import model.Match;
import model.Player;
import service.DBService;

public class PlayerDao implements DBService<Player> {
    @Override
    public int insertObject(Player obj) {
        return 0;
    }

    @Override
    public int deleteObjectById(Player obj) {
        return 0;
    }

    @Override
    public Player selectObjectById(int id) {
        return null;
    }

    @Override
    public int updateObject(int id, Player obj) {
        return 0;
    }
}
