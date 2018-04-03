package service;

import model.MongoDBObject;
import model.Player;

public interface DBService<T> {

    int insertObject(T obj);

    int deleteObjectById(T obj);

    T selectObjectById(int id);

    int updateObject(int id, T obj);

}
